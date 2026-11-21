package org.mob.replication_addon_tiers.block.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.buuz135.replication.block.tile.ReplicationMachine;
import com.buuz135.replication.item.MemoryChipItem;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.client.screen.addon.TextScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.component.button.ButtonComponent;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mob.replication_addon_tiers.client.gui.addon.PatternTransferUnitAddon;
import org.mob.replication_addon_tiers.pattern.ChipPatternData;
import org.mob.replication_addon_tiers.registry.ModRegistry;

/**
 * Transfers the patterns of the left chip to the right chip.
 * <p>
 * Supports two toggleable modes:
 * <ul>
 *     <li><b>Copy Mode (default):</b> All-or-nothing transfer. The left chip keeps all of its patterns. If the right
 *     chip lacks free slots for every new pattern, nothing is transferred and it displays a warning.</li>
 *     <li><b>Move Mode:</b> Transfers non-duplicate patterns that fit (plus duplicates where the left chip has a higher
 *     completion) and deletes the successfully transferred patterns from the left chip.</li>
 * </ul>
 */
public class PatternTransferUnitBlockEntity extends ReplicationMachine<PatternTransferUnitBlockEntity> {

    public static final int TICKS_PER_PATTERN = 5;
    public static final int ENERGY_PER_PATTERN = 500;
    private static final int ENERGY_PER_TICK = ENERGY_PER_PATTERN / TICKS_PER_PATTERN;

    // Warning text of the screen: centered between the two slots, above them, clear of the energy bar.
    private static final int TEXT_CENTER_X = 90;
    private static final int TEXT_Y = 16;
    private static final int TEXT_MAX_WIDTH = 120;
    private static final int TEXT_LINE_HEIGHT = 10;

    // Mode toggle button positioned below the progress arrow between the two chip slots.
    private static final int MODE_BUTTON_X = 76;
    private static final int MODE_BUTTON_Y = 68;

    @Save
    private SidedInventoryComponent<?> source;
    @Save
    private SidedInventoryComponent<?> target;
    @Save
    private ProgressBarComponent<?> progressBar;

    // False = Copy Mode, True = Move Mode.
    @Save
    private boolean moveMode = false;

    // Patterns the next transfer will write, 0 if there is nothing to do or not enough room.
    @Save
    private int pendingCount = 0;
    // True when the target lacks free slots for the transfer. Synced to the client for the screen.
    @Save
    private boolean notEnoughRoom = false;

    // Set when a chip changes, the plan is then worked out again on the next server tick.
    private boolean planDirty = true;

    /**
     * Result of a transfer: the full pattern list of the target once updated, the patterns that stay on the source
     * in move mode, the number of patterns written (added or improved), and the number that would not fit.
     */
    private record TransferPlan(
            List<CompoundTag> targetPatterns,
            List<CompoundTag> remainingSourcePatterns,
            int transferred,
            int overflow
    ) {
    }

    @SuppressWarnings("unchecked")
    public PatternTransferUnitBlockEntity(BasicTileBlock<PatternTransferUnitBlockEntity> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(base, blockEntityType != null ? blockEntityType : ModRegistry.PATTERN_TRANSFER_UNIT_BE.get(), pos, state);

        this.source = (SidedInventoryComponent<?>) new SidedInventoryComponent<>("source", 44, 48, 1, 0)
                .disableFacingAddon()
                .setInputFilter((stack, slot) -> stack.getItem() instanceof MemoryChipItem)
                .setOutputFilter((stack, slot) -> !this.moveMode || this.pendingCount <= 0)
                .setSlotLimit(1)
                .setOnSlotChanged((stack, slot) -> onChipChanged(this.source))
                .setColorGuiEnabled(false);
        this.addInventory((InventoryComponent<PatternTransferUnitBlockEntity>) this.source);

        this.progressBar = new ProgressBarComponent<>(72, 48, TICKS_PER_PATTERN)
                .setCanIncrease(harness -> this.pendingCount > 0 && this.getEnergyStorage().getEnergyStored() >= ENERGY_PER_TICK)
                // Keep the progress while waiting for energy, only reset it once finished or when there is nothing to do.
                .setCanReset(harness -> this.pendingCount <= 0 || this.progressBar.getProgress() >= this.progressBar.getMaxProgress())
                .setOnTickWork(() -> {
                    this.getEnergyStorage().extractEnergy(ENERGY_PER_TICK, false);
                    syncObject(this.progressBar);
                })
                .setOnFinishWork(this::transferPatterns)
                .setBarDirection(ProgressBarComponent.BarDirection.ARROW_RIGHT);
        this.addProgressBar((ProgressBarComponent<PatternTransferUnitBlockEntity>) this.progressBar);

        this.target = (SidedInventoryComponent<?>) new SidedInventoryComponent<>("target", 106, 48, 1, 0)
                .disableFacingAddon()
                .setInputFilter((stack, slot) -> stack.getItem() instanceof MemoryChipItem)
                .setOutputFilter((stack, slot) -> this.pendingCount <= 0)
                .setSlotLimit(1)
                .setOnSlotChanged((stack, slot) -> onChipChanged(this.target))
                .setColorGuiEnabled(false);
        this.addInventory((InventoryComponent<PatternTransferUnitBlockEntity>) this.target);

        ButtonComponent modeButton = new ButtonComponent(MODE_BUTTON_X, MODE_BUTTON_Y, 14, 14) {
            @Override
            @OnlyIn(Dist.CLIENT)
            public List<IFactory<? extends IScreenAddon>> getScreenAddons() {
                return Collections.singletonList(() -> new PatternTransferUnitAddon(this, () -> moveMode));
            }
        };
        modeButton.setPredicate((player, tag) -> {
            this.moveMode = !this.moveMode;
            updatePlan();
        });
        this.addButton(modeButton);

        this.addGuiAddonFactory(() -> new CenteredText(TEXT_Y, ChatFormatting.RED.getColor()) {
            @Override
            public String getText() {
                if (!notEnoughRoom) return "";
                return Component.translatable("gui.replication_addon_tiers.pattern_transfer_unit.not_enough_room").getString();
            }
        });
    }

    private void onChipChanged(Object inventory) {
        syncObject(inventory);
        this.planDirty = true;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, PatternTransferUnitBlockEntity blockEntity) {
        if (this.planDirty) {
            this.planDirty = false;
            updatePlan();
        }
        super.serverTick(level, pos, state, blockEntity);
    }

    private void updatePlan() {
        TransferPlan plan = buildPlan();
        if (plan == null) {
            this.notEnoughRoom = false;
            this.pendingCount = 0;
        } else if (this.moveMode) {
            this.notEnoughRoom = plan.transferred() == 0 && plan.overflow() > 0;
            this.pendingCount = plan.transferred();
        } else {
            this.notEnoughRoom = plan.overflow() > 0;
            this.pendingCount = this.notEnoughRoom ? 0 : plan.transferred();
        }
        this.progressBar.setMaxProgress(Math.max(1, this.pendingCount * TICKS_PER_PATTERN));
        this.progressBar.setProgress(0);
        markForUpdate();
    }

    @Nullable
    private TransferPlan buildPlan() {
        ItemStack sourceChip = this.source.getStackInSlot(0);
        ItemStack targetChip = this.target.getStackInSlot(0);
        if (sourceChip.isEmpty() || targetChip.isEmpty()) return null;

        List<CompoundTag> targetPatterns = ChipPatternData.read(targetChip);
        List<CompoundTag> remainingSourcePatterns = new ArrayList<>();
        Map<CompoundTag, Integer> targetIndexByKey = new HashMap<>();
        for (int i = 0; i < targetPatterns.size(); i++) {
            targetIndexByKey.put(ChipPatternData.key(targetPatterns.get(i)), i);
        }
        int room = ChipPatternData.slots(targetChip) - targetPatterns.size();

        int transferred = 0;
        int overflow = 0;
        for (CompoundTag pattern : ChipPatternData.read(sourceChip)) {
            CompoundTag key = ChipPatternData.key(pattern);
            Integer index = targetIndexByKey.get(key);
            if (index != null) {
                if (ChipPatternData.completion(pattern) > ChipPatternData.completion(targetPatterns.get(index))) {
                    targetPatterns.set(index, pattern);
                    transferred++;
                } else {
                    remainingSourcePatterns.add(pattern);
                }
            } else if (room > 0) {
                targetIndexByKey.put(key, targetPatterns.size());
                targetPatterns.add(pattern);
                room--;
                transferred++;
            } else {
                overflow++;
                remainingSourcePatterns.add(pattern);
            }
        }
        return new TransferPlan(targetPatterns, remainingSourcePatterns, transferred, overflow);
    }

    // Writes the updated target chip and deletes successfully transferred patterns from left chip in move mode.
    private void transferPatterns() {
        TransferPlan plan = buildPlan();
        if (plan == null || plan.transferred() == 0) return;
        if (!this.moveMode && plan.overflow() > 0) return;

        ItemStack targetChip = this.target.getStackInSlot(0).copy();
        ChipPatternData.write(targetChip, plan.targetPatterns());
        this.target.setStackInSlot(0, targetChip);

        if (this.moveMode) {
            ItemStack sourceChip = this.source.getStackInSlot(0).copy();
            ChipPatternData.write(sourceChip, plan.remainingSourcePatterns());
            this.source.setStackInSlot(0, sourceChip);
        }
    }

    @NotNull
    @Override
    public PatternTransferUnitBlockEntity getSelf() {
        return this;
    }

    @Override
    public int getTitleColor() {
        return 0x72e567;
    }

    @Override
    public float getTitleYPos(float titleWidth, float screenWidth, float screenHeight, float guiWidth, float guiHeight) {
        return super.getTitleYPos(titleWidth, screenWidth, screenHeight, guiWidth, guiHeight) - 16;
    }

    // Text read on every frame, wrapped to TEXT_MAX_WIDTH (and on "\n"), each line centered on TEXT_CENTER_X.
    private abstract static class CenteredText extends TextScreenAddon {

        private final int color;

        CenteredText(int posY, int color) {
            super("", TEXT_CENTER_X, posY, false, color);
            this.color = color;
        }

        @Override
        public abstract String getText();

        @Override
        public void drawBackgroundLayer(GuiGraphics guiGraphics, Screen screen, IAssetProvider provider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
            String text = getText();
            if (text.isEmpty()) return;
            Font font = screen.getMinecraft().font;
            List<FormattedCharSequence> lines = font.split(FormattedText.of(text), TEXT_MAX_WIDTH);
            for (int i = 0; i < lines.size(); i++) {
                FormattedCharSequence line = lines.get(i);
                guiGraphics.drawString(font, line, guiX + TEXT_CENTER_X - font.width(line) / 2, guiY + getPosY() + i * TEXT_LINE_HEIGHT, this.color, false);
            }
        }
    }
}
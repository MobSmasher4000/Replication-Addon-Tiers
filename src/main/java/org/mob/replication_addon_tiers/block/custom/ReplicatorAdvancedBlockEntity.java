package org.mob.replication_addon_tiers.block.custom;

import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.api.MatterCalculationStatus;
import com.buuz135.replication.api.task.IReplicationTask;
import com.buuz135.replication.api.task.ReplicationTask;
import com.buuz135.replication.block.tile.ReplicationMachine;
import com.buuz135.replication.calculation.ReplicationCalculation;
import org.mob.replication_addon_tiers.client.gui.addons.ReplicatorCraftingAddon;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.api.client.IAsset;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.api.filter.FilterSlot;
import com.hrznstudio.titanium.api.redstone.IRedstoneReader;
import com.hrznstudio.titanium.api.redstone.IRedstoneState;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.redstone.RedstoneAction;
import com.hrznstudio.titanium.block.redstone.RedstoneManager;
import com.hrznstudio.titanium.block.redstone.RedstoneState;
import com.hrznstudio.titanium.client.screen.addon.ItemstackFilterScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.component.button.RedstoneControlButtonComponent;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.hrznstudio.titanium.filter.ItemStackFilter;
import com.hrznstudio.titanium.util.AssetUtil;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.mob.replication_addon_tiers.Config;
import org.mob.replication_addon_tiers.block.ReplicatorAdvancedBlock;
import org.mob.replication_addon_tiers.client.gui.addons.ReplicatorMotorAddon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReplicatorAdvancedBlockEntity extends ReplicationMachine<ReplicatorAdvancedBlockEntity> implements IRedstoneReader {
    public static final float LOWER_PROGRESS = 0.563F;
    @Save
    private int progress;
    @Save
    private int action;
    @Save
    private ProgressBarComponent<ReplicatorAdvancedBlockEntity> progressBarComponent;
    @Save
    private SidedInventoryComponent<ReplicatorAdvancedBlockEntity> output;
    @Save
    private String craftingTask;
    @Save
    private ItemStack craftingStack;
    private IReplicationTask cachedReplicationTask;
    @Save
    private RedstoneManager<RedstoneAction> redstoneManager;
    private RedstoneControlButtonComponent<RedstoneAction> redstoneButton;
    @Save
    private ItemStackFilter infiniteCrafting;
    private boolean hasEnclosure;
    private boolean hasMotor;
    @Save
    private int motorSpeedMultiplier;
    @Save
    private boolean isCurrentTaskAFailure;

    private int max_progress = ReplicationConfig.Replicator.MAX_PROGRESS - Config.replicatorAdvanced;

    public ReplicatorAdvancedBlockEntity(BasicTileBlock<ReplicatorAdvancedBlockEntity> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(base, blockEntityType, pos, state);
        this.progress = max_progress;
        this.action = 1;
        this.hasEnclosure = false;
        this.hasMotor = false;
        this.motorSpeedMultiplier = 100;
        this.isCurrentTaskAFailure = false;
        this.craftingStack = ItemStack.EMPTY;
        this.progressBarComponent = (new ProgressBarComponent(26, 25, 0, max_progress * 2)).setBarDirection(ProgressBarComponent.BarDirection.VERTICAL_UP);
        this.addProgressBar(this.progressBarComponent);
        this.output = (SidedInventoryComponent)(new SidedInventoryComponent("output", 42, 63, 7, 0)).setColor(14592060).setInputFilter((stack, integer) -> false).setColorGuiEnabled(false);
        this.addInventory(this.output);
        this.redstoneManager = new RedstoneManager(RedstoneAction.IGNORE, false);
        this.addButton(this.redstoneButton = new RedstoneControlButtonComponent(154, 84, 14, 14, () -> this.redstoneManager, () -> this));
        this.infiniteCrafting = new ItemStackFilter("infiniteCrafting", 1) {
            public void setFilter(int slot, ItemStack stack) {
                super.setFilter(slot, stack.getItem().getDefaultInstance());
            }

            @OnlyIn(Dist.CLIENT)
            public List<IFactory<? extends IScreenAddon>> getScreenAddons() {
                List<IFactory<? extends IScreenAddon>> list = new ArrayList();
                list.add((IFactory)() -> new ItemstackFilterScreenAddon(this) {
                    public void drawBackgroundLayer(GuiGraphics guiGraphics, Screen screen, IAssetProvider provider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
                        for(FilterSlot<ItemStack> filterSlot : ReplicatorAdvancedBlockEntity.this.infiniteCrafting.getFilterSlots()) {
                            if (filterSlot != null) {
                                AssetUtil.drawAsset(guiGraphics, screen, (IAsset) Objects.requireNonNull(provider.getAsset(AssetTypes.SLOT)), guiX + filterSlot.getX(), guiY + filterSlot.getY());
                                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                                if (!((ItemStack)filterSlot.getFilter()).isEmpty()) {
                                    Lighting.setupFor3DItems();
                                    guiGraphics.renderItem((ItemStack)filterSlot.getFilter(), filterSlot.getX() + guiX + 1, filterSlot.getY() + guiY + 1);
                                }
                            }
                        }

                    }
                });
                return list;
            }
        };
        this.infiniteCrafting.getFilterSlots()[0] = new FilterSlot(43, 28, 0, ItemStack.EMPTY);
        this.addFilter(this.infiniteCrafting);
    }

    @OnlyIn(Dist.CLIENT)
    public void initClient() {
        super.initClient();
        this.addGuiAddonFactory(() -> new ReplicatorCraftingAddon(50, 30, this));
        this.addGuiAddonFactory(() -> new ReplicatorMotorAddon(this, 7, 184));
    }

    public void serverTick(Level level, BlockPos pos, BlockState state, ReplicatorAdvancedBlockEntity blockEntity) {
        super.serverTick(level, pos, state, blockEntity);
        if (this.level.getGameTime() % 20L == 0L) {
            int maxProgress = max_progress * 2;
            this.hasEnclosure = (Boolean)state.getValue(ReplicatorAdvancedBlock.HAS_ENCLOSURE);
            this.hasMotor = (Boolean)state.getValue(ReplicatorAdvancedBlock.HAS_MOTOR);
            if (this.hasEnclosure) {
                maxProgress = (int)((double)maxProgress * 0.8);
            }

            if (this.hasMotor) {
                maxProgress = (int)((double)maxProgress * ((double)this.motorSpeedMultiplier / (double)100.0F));
            }

            this.progressBarComponent.setMaxProgress(maxProgress);
            if (this.progress > this.getMaxProgress()) {
                this.progress = this.getMaxProgress();
            }

            this.syncObject(this.progressBarComponent);
            this.syncObject(this.progress);
        }

        if (this.getNetwork() != null) {
            if (ReplicationCalculation.STATUS == MatterCalculationStatus.CALCULATED) {
                if (((RedstoneAction)this.redstoneManager.getAction()).canRun(this.getEnvironmentValue(false, (Direction)null)) && this.redstoneManager.shouldWork()) {
                    this.tickProgress();
                    this.progressBarComponent.setProgress(this.action == 1 ? this.getMaxProgress() - this.progress : this.getMaxProgress() + this.progress);
                    this.syncObject(this.progressBarComponent);
                    if (this.level.getGameTime() % 4L == 0L && this.craftingTask == null && this.cachedReplicationTask == null && !((ItemStack)this.infiniteCrafting.getFilterSlots()[0].getFilter()).isEmpty()) {
                        ReplicationTask task = new ReplicationTask(((ItemStack)this.infiniteCrafting.getFilterSlots()[0].getFilter()).copy(), 1, IReplicationTask.Mode.SINGLE, this.getBlockPos());
                        task.acceptReplicator(this.getBlockPos());
                        this.isCurrentTaskAFailure = this.level.getRandom().nextInt(100) < this.getFailureChance();
                        this.craftingTask = task.getUuid().toString();
                        this.cachedReplicationTask = task;
                        this.craftingStack = task.getReplicatingStack();
                        this.syncObject(this.craftingStack);
                        this.syncObject(this.isCurrentTaskAFailure);
                        this.getNetwork().getTaskManager().getPendingTasks().put(task.getUuid().toString(), task);
                        this.getNetwork().onTaskValueChanged(task, (ServerLevel)this.level);
                    }

                    if (this.level.getGameTime() % 4L == 0L && this.craftingTask == null) {
                        IReplicationTask task = this.getNetwork().getTaskManager().findTaskForReplicator(this.getBlockPos(), this.getNetwork());
                        if (task != null) {
                            task.acceptReplicator(this.getBlockPos());
                            this.isCurrentTaskAFailure = this.level.getRandom().nextInt(100) < this.getFailureChance();
                            this.craftingTask = task.getUuid().toString();
                            this.cachedReplicationTask = task;
                            this.craftingStack = task.getReplicatingStack();
                            this.syncObject(this.craftingStack);
                            this.syncObject(this.isCurrentTaskAFailure);
                            this.getNetwork().onTaskValueChanged(task, (ServerLevel)this.level);
                        }
                    }

                    if (this.level.getGameTime() % 4L == 0L && this.craftingTask != null && this.cachedReplicationTask == null) {
                        if (this.getNetwork().getTaskManager().getPendingTasks().containsKey(this.craftingTask)) {
                            this.cachedReplicationTask = (IReplicationTask)this.getNetwork().getTaskManager().getPendingTasks().get(this.craftingTask);
                            this.craftingStack = this.cachedReplicationTask.getReplicatingStack();
                            this.syncObject(this.craftingStack);
                        } else {
                            this.cancelTask();
                        }
                    }

                    if (this.level.getGameTime() % 4L == 0L && this.craftingTask != null && this.cachedReplicationTask != null && !this.cachedReplicationTask.getStoredMatterStack().containsKey(this.getBlockPos().asLong())) {
                        this.cachedReplicationTask.storeMatterStacksFor(this.level, this.getBlockPos(), this.getNetwork());
                    }
                }

            }
        }
    }

    public @NotNull ReplicatorAdvancedBlockEntity getSelf() {
        return this;
    }

    public void clientTick(Level level, BlockPos pos, BlockState state, ReplicatorAdvancedBlockEntity blockEntity) {
        super.clientTick(level, pos, state, blockEntity);
    }

    private void tickProgress() {
        if (this.craftingTask != null && this.getEnergyStorage().getEnergyStored() > this.getPowerConsumption() && this.cachedReplicationTask != null && this.cachedReplicationTask.getStoredMatterStack().containsKey(this.getBlockPos().asLong())) {
            if (this.action == 0) {
                if (this.progress >= this.getMaxProgress()) {
                    if (!this.isCurrentTaskAFailure) {
                        if (ItemHandlerHelper.insertItem(this.output, this.craftingStack.copy(), true).isEmpty()) {
                            this.action = 1;
                            this.syncObject(this.action);
                            this.replicateItem();
                        }
                    } else {
                        this.isCurrentTaskAFailure = this.level.getRandom().nextInt(100) < this.getFailureChance();
                        this.action = 1;
                        this.syncObject(this.action);
                        this.syncObject(this.isCurrentTaskAFailure);
                    }
                } else {
                    this.getEnergyStorage().extractEnergy(this.getPowerConsumption(), false);
                    ++this.progress;
                }

                this.syncObject(this.progress);
            } else {
                --this.progress;
                this.getEnergyStorage().extractEnergy(this.getPowerConsumption(), false);
                this.syncObject(this.progress);
                if (this.progress <= 0) {
                    this.action = 0;
                    this.syncObject(this.action);
                }
            }

            this.markComponentDirty();
        }

        if (this.craftingTask == null && this.progress < this.getMaxProgress()) {
            this.action = 1;
            ++this.progress;
            this.syncObject(this.action);
            this.syncObject(this.progress);
        }

    }

    protected @NotNull EnergyStorageComponent<ReplicatorAdvancedBlockEntity> createEnergyStorage() {
        return new EnergyStorageComponent(25000, 7, 25);
    }

    private void replicateItem() {
        this.cachedReplicationTask.finalizeReplication(this.level, this.getBlockPos(), this.getNetwork());
        this.getNetwork().onTaskValueChanged(this.cachedReplicationTask, (ServerLevel)this.level);
        if (!this.getBlockPos().equals(this.cachedReplicationTask.getSource())) {
            IItemHandler capability = (IItemHandler)this.level.getCapability(Capabilities.ItemHandler.BLOCK, this.cachedReplicationTask.getSource(), Direction.UP);
            if (capability != null) {
                if (!ItemHandlerHelper.insertItem(capability, this.cachedReplicationTask.getReplicatingStack().copyWithCount(1), false).isEmpty()) {
                    ItemHandlerHelper.insertItem(this.output, this.cachedReplicationTask.getReplicatingStack().copyWithCount(1), false);
                }
            } else {
                ItemHandlerHelper.insertItem(this.output, this.cachedReplicationTask.getReplicatingStack().copyWithCount(1), false);
            }
        } else {
            ItemHandlerHelper.insertItem(this.output, this.cachedReplicationTask.getReplicatingStack().copyWithCount(1), false);
        }

        this.cachedReplicationTask = null;
        this.craftingStack = ItemStack.EMPTY;
        this.craftingTask = null;
        this.redstoneManager.finish();
        this.syncObject(this.craftingStack);
    }

    public void setRemoved() {
        if (this.cachedReplicationTask != null) {
            this.cachedReplicationTask.getReplicatorsOnTask().remove(this.getBlockPos().asLong());
            if (this.cachedReplicationTask.getReplicatorsOnTask().isEmpty()) {
                this.getNetwork().cancelTask(this.craftingTask, this.level);
            }
        }

        super.setRemoved();
    }

    public void cancelTask() {
        this.cachedReplicationTask = null;
        this.craftingStack = ItemStack.EMPTY;
        this.craftingTask = null;
        this.redstoneManager.finish();
        this.syncObject(this.craftingStack);
    }

    public int getProgress() {
        return this.progress;
    }

    public int getMaxProgress() {
        return this.progressBarComponent.getMaxProgress() / 2;
    }

    public int getPowerConsumption() {
        int power = ReplicationConfig.Replicator.POWER_TICK;
        if (this.hasEnclosure) {
            power = (int)Math.ceil((double)power * ReplicationConfig.Replicator.ENCLOSURE_POWER_MULTIPLIER);
        }

        return power;
    }

    public boolean hasMotor() {
        return this.hasMotor;
    }

    public boolean hasEnclosure() {
        return this.hasEnclosure;
    }

    public int getMotorSpeedMultiplier() {
        return this.motorSpeedMultiplier;
    }

    public void handleButtonMessage(int id, Player playerEntity, CompoundTag compound) {
        super.handleButtonMessage(id, playerEntity, compound);
        if (id == 124578) {
            this.motorSpeedMultiplier = compound.getInt("Multiplier");
            if (this.motorSpeedMultiplier > 100) {
                this.motorSpeedMultiplier = 100;
            }

            if (this.motorSpeedMultiplier < 20) {
                this.motorSpeedMultiplier = 20;
            }

            this.syncObject(this.motorSpeedMultiplier);
        }

    }

    public int getFailureChance() {
        int value = this.getMotorSpeedMultiplier();
        int oldMin = 20;
        int oldMax = 100;
        int newMin = 0;
        int newMax = 50;
        if (value < oldMin) {
            value = oldMin;
        }

        if (value > oldMax) {
            value = oldMax;
        }

        int scaled = (int)Math.floor((double)((oldMax - value) * (newMax - newMin)) / (double)(oldMax - oldMin) + (double)newMin);
        return scaled;
    }

    public boolean isCurrentTaskAFailure() {
        return this.isCurrentTaskAFailure;
    }

    public int getAction() {
        return this.action;
    }

    public ItemStack getCraftingStack() {
        return this.craftingStack;
    }

    public boolean isInfinite() {
        return !((ItemStack)this.infiniteCrafting.getFilterSlots()[0].getFilter()).isEmpty();
    }

    public IRedstoneState getEnvironmentValue(boolean strongPower, Direction direction) {
        if (strongPower) {
            if (direction == null) {
                return this.level.hasNeighborSignal(this.worldPosition) ? RedstoneState.ON : RedstoneState.OFF;
            } else {
                return this.level.hasSignal(this.worldPosition, direction) ? RedstoneState.ON : RedstoneState.OFF;
            }
        } else {
            return this.level.getBestNeighborSignal(this.worldPosition) > 0 ? RedstoneState.ON : RedstoneState.OFF;
        }
    }

    public void onNeighborChanged(Block blockIn, BlockPos fromPos) {
        super.onNeighborChanged(blockIn, fromPos);
        this.redstoneManager.setLastRedstoneState(this.getEnvironmentValue(false, (Direction)null).isReceivingRedstone());
    }

    public int getTitleColor() {
        return 7529831;
    }

    public float getTitleYPos(float titleWidth, float screenWidth, float screenHeight, float guiWidth, float guiHeight) {
        return super.getTitleYPos(titleWidth, screenWidth, screenHeight, guiWidth, guiHeight) - 16.0F;
    }
}

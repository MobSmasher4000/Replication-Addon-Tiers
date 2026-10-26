package org.mob.replication_addon_tiers.block.entity.disintegrator;

import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.api.MatterCalculationStatus;
import com.buuz135.replication.api.matter_fluid.IMatterTank;
import com.buuz135.replication.api.matter_fluid.MatterStack;
import com.buuz135.replication.api.matter_fluid.component.MatterTankComponent;
import com.buuz135.replication.api.network.IMatterTanksSupplier;
import com.buuz135.replication.block.tile.ReplicationMachine;
import com.buuz135.replication.calculation.MatterValue;
import com.buuz135.replication.calculation.ReplicationCalculation;
import com.buuz135.replication.util.InvUtil;
import com.buuz135.replication.util.NumberUtils;
import com.buuz135.replication.util.ReplicationTags;
import com.buuz135.replication.util.TransferUtil;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.hrznstudio.titanium.component.sideness.IFacingComponent;
import com.hrznstudio.titanium.util.FacingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.mob.replication_addon_tiers.ModConfig;
import org.mob.replication_addon_tiers.client.gui.addons.AdvancedDisintegratorAddon;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class AdvancedDisintegratorBlockEntity extends ReplicationMachine<AdvancedDisintegratorBlockEntity> implements IMatterTanksSupplier {

    @Save
    private SidedInventoryComponent<?> input;
    @Save
    private ProgressBarComponent<?> progressBarComponent;
    @Save
    private MatterTankComponent<AdvancedDisintegratorBlockEntity> tank1;
    @Save
    private MatterTankComponent<AdvancedDisintegratorBlockEntity> tank2;
    @Save
    private MatterTankComponent<AdvancedDisintegratorBlockEntity> tank3;
    @Save
    private MatterTankComponent<AdvancedDisintegratorBlockEntity> tank4;
    @Save
    private MatterTankComponent<AdvancedDisintegratorBlockEntity> tank5;
    @Save
    private MatterTankComponent<AdvancedDisintegratorBlockEntity> tank6;

    private Queue<MatterStack> queuedMatterStacks;

    private final int MAX_PROGRESS = ModConfig.Disintegrator.Advanced.MAX_PROGRESS;
    private final int TANK_CAPACITY = ModConfig.Disintegrator.Advanced.TANK_CAPACITY;

    public AdvancedDisintegratorBlockEntity(BasicTileBlock<AdvancedDisintegratorBlockEntity> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(base, blockEntityType, pos, state);
        this.queuedMatterStacks = new ArrayDeque<>();
        this.input = (SidedInventoryComponent<?>) new SidedInventoryComponent<>("input", 29, 30, 3, 0)
                .disableFacingAddon()
                .setInputFilter((itemStack, integer) -> ReplicationCalculation.getMatterCompound(itemStack) != null)
                .setOutputFilter((itemStack, integer) -> false)
                .setSlotLimit(64)
                .setSlotPosition(integer -> Pair.of(0, 18*integer))
                .setOnSlotChanged((stack, integer) -> syncObject(this.input))
                .setColorGuiEnabled(false)
        ;
        InvUtil.disableAllSidesAndEnable(this.input, state.getValue(RotatableBlock.FACING_HORIZONTAL), IFacingComponent.FaceMode.ENABLED, FacingUtil.Sideness.BOTTOM, FacingUtil.Sideness.BACK, FacingUtil.Sideness.TOP);
        this.addInventory((InventoryComponent<AdvancedDisintegratorBlockEntity>) input);

        this.progressBarComponent = new ProgressBarComponent<>(48, 28, MAX_PROGRESS)
                .setOnTickWork(() -> {
                    syncObject(this.progressBarComponent);
                })
                .setCanIncrease(iComponentHarness -> {
                    if (ReplicationCalculation.STATUS != MatterCalculationStatus.CALCULATED) return false;
                    if (!queuedMatterStacks.isEmpty()) return false;
                    if (this.getEnergyStorage().getEnergyStored() < ReplicationConfig.Disintegrator.POWER_USAGE) return false;
                    for (int i = 0; i < this.input.getSlots(); i++) {
                        var stack = this.input.getStackInSlot(i);
                        if (!stack.isEmpty()) {return true;}
                    }
                    return false;
                })
                .setOnFinishWork(this::onFinish)
                .setBarDirection(ProgressBarComponent.BarDirection.VERTICAL_UP);

        this.addProgressBar((ProgressBarComponent<AdvancedDisintegratorBlockEntity>) this.progressBarComponent);

        this.tank1 = this.createMatterTank(1);
        this.tank2 = this.createMatterTank(2);
        this.tank3 = this.createMatterTank(3);
        this.tank4 = this.createMatterTank(4);
        this.tank5 = this.createMatterTank(5);
        this.tank6 = this.createMatterTank(6);

        this.addMatterTank(this.tank1);
        this.addMatterTank(this.tank2);
        this.addMatterTank(this.tank3);
        this.addMatterTank(this.tank4);
        this.addMatterTank(this.tank5);
        this.addMatterTank(this.tank6);
    }

    @Override
    public void initClient() {
        super.initClient();
        addGuiAddonFactory(() -> new AdvancedDisintegratorAddon(50, 30, this));
    }

    private void onFinish() {
        for (int i = 0; i < this.input.getSlots(); i++) {
            var stack = this.input.getStackInSlot(i);
            if (!stack.isEmpty() && !stack.is(ReplicationTags.CANT_BE_DISINTEGRATED) && this.getEnergyStorage().getEnergyStored() >= ReplicationConfig.Disintegrator.POWER_USAGE){
                var data = ReplicationCalculation.getMatterCompound(stack);
                if (data != null) {
                    for (MatterValue matterValue : data.getValues().values()) {
                        queuedMatterStacks.add(new MatterStack(matterValue.getMatter(), NumberUtils.customCeil(matterValue.getAmount())));
                        markComponentDirty();
                    }
                    stack.shrink(1);
                    this.input.setStackInSlot(i, stack);
                    TransferUtil.extractEnergy(this.getEnergyStorage(), ReplicationConfig.Disintegrator.POWER_USAGE, false);
                }
            }
        }
        syncObject(this.input);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, AdvancedDisintegratorBlockEntity blockEntity) {
        super.serverTick(level, pos, state, blockEntity);
        if (this.level.getGameTime() % 5 == 0) splitItems();
        if (!this.queuedMatterStacks.isEmpty()){
            var peekedElement = this.queuedMatterStacks.peek();
            if (peekedElement.getAmount() < 0) {
                this.queuedMatterStacks.poll();
                return;
            }
            for (MatterTankComponent<AdvancedDisintegratorBlockEntity> matterTankComponent : this.getMatterTankComponents()) {
                if (!matterTankComponent.isEmpty() && matterTankComponent.getMatter().isMatterEqual(peekedElement) && matterTankComponent.fillForced(peekedElement, IFluidHandler.FluidAction.SIMULATE) <= peekedElement.getAmount()){
                    peekedElement.setAmount(peekedElement.getAmount() - matterTankComponent.fillForced(peekedElement, IFluidHandler.FluidAction.EXECUTE));
                    markComponentDirty();
                    if (peekedElement.isEmpty()){
                        this.queuedMatterStacks.poll();
                        return;
                    }
                }
            }
            if (!peekedElement.isEmpty()){
                for (MatterTankComponent<AdvancedDisintegratorBlockEntity> matterTankComponent : this.getMatterTankComponents()) {
                    if (matterTankComponent.isEmpty()){
                        peekedElement.setAmount(peekedElement.getAmount() - matterTankComponent.fillForced(peekedElement, IFluidHandler.FluidAction.EXECUTE));
                        markComponentDirty();
                        if (peekedElement.isEmpty()){
                            this.queuedMatterStacks.poll();
                            return;
                        }
                    }
                }
            }
        }
    }

    private MatterTankComponent<AdvancedDisintegratorBlockEntity> createMatterTank(int index){
        return new MatterTankComponent<AdvancedDisintegratorBlockEntity>("tank" + index, TANK_CAPACITY, 42 + index * 18, 28).setTankAction(FluidTankComponent.Action.DRAIN);
    }

    @NotNull
    @Override
    public AdvancedDisintegratorBlockEntity getSelf() {
        return this;
    }

    @Override
    public List<? extends IMatterTank> getTanks() {
        return this.getMatterTankComponents();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    public SidedInventoryComponent<?> getInput() {
        return input;
    }

    @Override
    public int getTitleColor() {
        return 0xff72e567;
    }

    @Override
    public float getTitleYPos(float titleWidth, float screenWidth, float screenHeight, float guiWidth, float guiHeight) {
        return super.getTitleYPos(titleWidth, screenWidth, screenHeight, guiWidth, guiHeight) - 16;
    }

    public void splitItems() {
        for (int i = 0; i < this.input.getSlots(); i++) {
            if (!this.input.getStackInSlot(i).isEmpty()) {
                var stack = this.input.getStackInSlot(i);
                for (int j = 0; j < this.input.getSlots(); j++) {
                    if (i == j) continue;
                    var otherSlot = this.input.getStackInSlot(j);
                    if (stack.getCount() > 1 && this.input.getStackInSlot(j).isEmpty()) {
                        var copied = stack.copyWithCount(1);
                        stack.shrink(1);
                        this.input.setStackInSlot(j, copied);
                        this.input.setStackInSlot(i, stack);
                    } else if (ItemStack.isSameItemSameComponents(stack, otherSlot) && stack.getCount() > otherSlot.getCount()) {
                        var copied = otherSlot.copyWithCount(otherSlot.getCount() + 1);
                        stack.shrink(1);
                        this.input.setStackInSlot(j, copied);
                        this.input.setStackInSlot(i, stack);
                    }
                }
            }
        }
    }

    @Override
    public void loadAdditional(ValueInput compound) {
        super.loadAdditional(compound);
        var storedMatterStacks = compound.read("queuedMatterStacks", CompoundTag.CODEC);
        if (storedMatterStacks.isPresent()) {
            this.queuedMatterStacks.clear();
            var queuedMatterStacks = storedMatterStacks.get();
            for (String allKey : queuedMatterStacks.keySet()) {
                this.queuedMatterStacks.add(MatterStack.loadMatterStackFromNBT(queuedMatterStacks.getCompoundOrEmpty(allKey)));
            }
        }
    }

    @Override
    protected void saveAdditional(ValueOutput compoundTag) {
        super.saveAdditional(compoundTag);
        var queuedMatterStacks = new CompoundTag();
        var i = 0;
        for (MatterStack queuedMatterStack : this.queuedMatterStacks) {
            queuedMatterStacks.put(i + "", queuedMatterStack.writeToNBT(new CompoundTag()));
            ++i;
        }
        compoundTag.store("queuedMatterStacks", CompoundTag.CODEC, queuedMatterStacks);
    }

    @Override
    protected @NonNull EnergyStorageComponent<AdvancedDisintegratorBlockEntity> createEnergyStorage() {
        return new EnergyStorageComponent<>(50000, 9, 28);
    }
}
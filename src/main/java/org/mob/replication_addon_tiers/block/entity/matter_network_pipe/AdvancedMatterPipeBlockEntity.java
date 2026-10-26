package org.mob.replication_addon_tiers.block.entity.matter_network_pipe;

import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.block.tile.NetworkBlockEntity;
import com.buuz135.replication.util.TransferUtil;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block_network.element.NetworkElement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.jetbrains.annotations.NotNull;
import org.mob.replication_addon_tiers.ModConfig;

public class AdvancedMatterPipeBlockEntity extends NetworkBlockEntity<AdvancedMatterPipeBlockEntity> {


    @Save
    private boolean needsToRecreateEnergyStorage;
    private final int POWER_TRANSFER = ReplicationConfig.MatterPipe.POWER_TRANSFER * ModConfig.MatterPipe.ADVANCED_MATTER_PIPE;

    public AdvancedMatterPipeBlockEntity(BasicTileBlock<AdvancedMatterPipeBlockEntity> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(base, blockEntityType, pos, state);
        this.needsToRecreateEnergyStorage = false;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, AdvancedMatterPipeBlockEntity blockEntity) {
        super.serverTick(level, pos, state, blockEntity);

        if (level.getGameTime() % 2 == 0){
            if (needsToRecreateEnergyStorage) {
                level.invalidateCapabilities(this.worldPosition);
                this.needsToRecreateEnergyStorage = false;
                this.level.updateNeighborsAt(this.worldPosition, this.getBlockState().getBlock());
            }
            for (Direction value : Direction.values()) {
                var capability = this.level.getCapability(Capabilities.Energy.BLOCK, this.worldPosition.relative(value), value.getOpposite());
                var tile = this.level.getBlockEntity(this.worldPosition.relative(value));
                if (capability != null && !(tile instanceof AdvancedMatterPipeBlockEntity) && this.getNetwork() != null){
                    var simulatedExtract = this.getNetwork().getEnergyStorage().extractEnergy(POWER_TRANSFER, true);
                    var realExtracted = TransferUtil.insertEnergy(capability, simulatedExtract, false);
                    this.getNetwork().getEnergyStorage().extractEnergy(realExtracted, false);
                }
            }
        }
    }

    @NotNull
    @Override
    public AdvancedMatterPipeBlockEntity getSelf() {
        return this;
    }

    @Override
    protected NetworkElement createElement(Level level, BlockPos pos) {
        return super.createElement(level, pos);
    }

    public void setNeedsToRecreateEnergyStorage(boolean needsToRecreateEnergyStorage) {
        this.needsToRecreateEnergyStorage = needsToRecreateEnergyStorage;
    }
}
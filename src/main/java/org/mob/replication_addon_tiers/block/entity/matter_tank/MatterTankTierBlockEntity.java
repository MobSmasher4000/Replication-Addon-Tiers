package org.mob.replication_addon_tiers.block.entity.matter_tank;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.mob.replication_addon_tiers.ReplicationAddonTiersRegistry;
import org.mob.replication_addon_tiers.block.custom.matter_tank.MatterTankTierBlock;

public class MatterTankTierBlockEntity extends BaseMatterTankTierBlockEntity<MatterTankTierBlockEntity> {

    public MatterTankTierBlockEntity(MatterTankTierBlock block, BlockPos pos, BlockState state) {
        super(
            block.getTier().getDisplayName(),
            block.getTier().getCapacity(),
            block,
            ReplicationAddonTiersRegistry.Blocks.getMatterTankType(block.getTier()), 
            pos,
            state,
            () -> block.getTier().isCreative()
        );
    }

    @Override
    public @NonNull MatterTankTierBlockEntity getSelf() {
        return this;
    }
}
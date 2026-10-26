package org.mob.replication_addon_tiers.compat.jade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import org.mob.replication_addon_tiers.block.entity.matter_tank.BaseMatterTankTierBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public class MatterTankTierDataProvider implements IServerDataProvider<BlockAccessor> {

    @Override
    public Identifier getUid() {
        return MatterTankTierProvider.MATTER_TANK_LOCATION;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof BaseMatterTankTierBlockEntity<?> blockEntity) {
            if (blockEntity.getTanks() != null && !blockEntity.getTanks().isEmpty()) {
                compoundTag.put("MatterStack", blockEntity.getTanks().get(0).getMatter().writeToNBT(new CompoundTag()));
            }
        }
    }
}
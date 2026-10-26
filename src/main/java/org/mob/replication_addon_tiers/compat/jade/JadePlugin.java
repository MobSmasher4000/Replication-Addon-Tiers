package org.mob.replication_addon_tiers.compat.jade;

import org.mob.replication_addon_tiers.block.custom.matter_tank.MatterTankTierBlock;
import org.mob.replication_addon_tiers.block.entity.matter_tank.MatterTankTierBlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new MatterTankTierDataProvider(), MatterTankTierBlockEntity.class);
    }

    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new MatterTankTierProvider(), MatterTankTierBlock.class);
    }


}

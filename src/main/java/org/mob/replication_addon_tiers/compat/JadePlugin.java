package org.mob.replication_addon_tiers.compat;

import org.mob.replication_addon_tiers.block.MatterTankTier1Block;
import org.mob.replication_addon_tiers.block.MatterTankTier2Block;
import org.mob.replication_addon_tiers.block.MatterTankTier3Block;
import org.mob.replication_addon_tiers.block.MatterTankTier4Block;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier1BlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier2BlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier3BlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier4BlockEntity;
import org.mob.replication_addon_tiers.compat.jade.MatterTankTier1ComponentProvider;
import org.mob.replication_addon_tiers.compat.jade.MatterTankTier2ComponentProvider;
import org.mob.replication_addon_tiers.compat.jade.MatterTankTier3ComponentProvider;
import org.mob.replication_addon_tiers.compat.jade.MatterTankTier4ComponentProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    public static MatterTankTier1ComponentProvider componentProvider1 = new MatterTankTier1ComponentProvider();
    public static MatterTankTier2ComponentProvider componentProvider2 = new MatterTankTier2ComponentProvider();
    public static MatterTankTier3ComponentProvider componentProvider3 = new MatterTankTier3ComponentProvider();
    public static MatterTankTier4ComponentProvider componentProvider4 = new MatterTankTier4ComponentProvider();

    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(componentProvider1, MatterTankTier1BlockEntity.class);
        registration.registerBlockDataProvider(componentProvider2, MatterTankTier2BlockEntity.class);
        registration.registerBlockDataProvider(componentProvider3, MatterTankTier3BlockEntity.class);
        registration.registerBlockDataProvider(componentProvider4, MatterTankTier4BlockEntity.class);
    }

    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(componentProvider1, MatterTankTier1Block.class);
        registration.registerBlockComponent(componentProvider2, MatterTankTier2Block.class);
        registration.registerBlockComponent(componentProvider3, MatterTankTier3Block.class);
        registration.registerBlockComponent(componentProvider4, MatterTankTier4Block.class);
    }


}

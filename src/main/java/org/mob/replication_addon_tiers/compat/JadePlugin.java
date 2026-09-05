package org.mob.replication_addon_tiers.compat;

import org.mob.replication_addon_tiers.block.*;
import org.mob.replication_addon_tiers.block.custom.matterTank.*;
import org.mob.replication_addon_tiers.compat.jade.*;
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
    public static MatterTankTier5ComponentProvider componentProvider5 = new MatterTankTier5ComponentProvider();
    public static MatterTankTier6ComponentProvider componentProvider6 = new MatterTankTier6ComponentProvider();
    public static MatterTankTier7ComponentProvider componentProvider7 = new MatterTankTier7ComponentProvider();
    public static MatterTankTier8ComponentProvider componentProvider8 = new MatterTankTier8ComponentProvider();

    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(componentProvider1, MatterTankTier1BlockEntity.class);
        registration.registerBlockDataProvider(componentProvider2, MatterTankTier2BlockEntity.class);
        registration.registerBlockDataProvider(componentProvider3, MatterTankTier3BlockEntity.class);
        registration.registerBlockDataProvider(componentProvider4, MatterTankTier4BlockEntity.class);
        registration.registerBlockDataProvider(componentProvider5, MatterTankTier5BlockEntity.class);
        registration.registerBlockDataProvider(componentProvider6, MatterTankTier6BlockEntity.class);
        registration.registerBlockDataProvider(componentProvider7, MatterTankTier7BlockEntity.class);
        registration.registerBlockDataProvider(componentProvider8, MatterTankTier8BlockEntity.class);
    }

    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(componentProvider1, MatterTankTier1Block.class);
        registration.registerBlockComponent(componentProvider2, MatterTankTier2Block.class);
        registration.registerBlockComponent(componentProvider3, MatterTankTier3Block.class);
        registration.registerBlockComponent(componentProvider4, MatterTankTier4Block.class);
        registration.registerBlockComponent(componentProvider5, MatterTankTier5Block.class);
        registration.registerBlockComponent(componentProvider6, MatterTankTier6Block.class);
        registration.registerBlockComponent(componentProvider7, MatterTankTier7Block.class);
        registration.registerBlockComponent(componentProvider8, MatterTankTier8Block.class);
    }


}

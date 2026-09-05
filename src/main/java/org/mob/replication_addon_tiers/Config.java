package org.mob.replication_addon_tiers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = ReplicationAddonTiers.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue TANK_TIER_1 = BUILDER.comment("Multiplier for Matter Tank Tier 1").defineInRange("tankTier1", 2, 1, 32);
    private static final ModConfigSpec.IntValue TANK_TIER_2 = BUILDER.comment("Multiplier for Matter Tank Tier 2").defineInRange("tankTier2", 4, 2, 32);
    private static final ModConfigSpec.IntValue TANK_TIER_3 = BUILDER.comment("Multiplier for Matter Tank Tier 3").defineInRange("tankTier3", 8, 3, 32);
    private static final ModConfigSpec.IntValue TANK_TIER_4 = BUILDER.comment("Multiplier for Matter Tank Tier 4").defineInRange("tankTier4", 16, 4, 32);
    private static final ModConfigSpec.IntValue TANK_TIER_5 = BUILDER.comment("Multiplier for Matter Tank Tier 5").defineInRange("tankTier5", 32, 5, 1024);
    private static final ModConfigSpec.IntValue TANK_TIER_6 = BUILDER.comment("Multiplier for Matter Tank Tier 6").defineInRange("tankTier6", 64, 6, 1024);
    private static final ModConfigSpec.IntValue TANK_TIER_7 = BUILDER.comment("Multiplier for Matter Tank Tier 7").defineInRange("tankTier7", 128, 7, 1024);
    private static final ModConfigSpec.IntValue TANK_TIER_8 = BUILDER.comment("Multiplier for Matter Tank Tier 8").defineInRange("tankTier8", 256, 8, 1024);

    private static final ModConfigSpec.IntValue ADVANCED_REPLICATOR = BUILDER.comment(" nummber of ticks it takes for Replication task \n it takes twice the ticks mentioned in the config inside game.").defineInRange("advancedReplicator", 4 , 1, 100);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int tankTier1;
    public static int tankTier2;
    public static int tankTier3;
    public static int tankTier4;
    public static int tankTier5;
    public static int tankTier6;
    public static int tankTier7;
    public static int tankTier8;
    public static int advancedReplicator;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        tankTier1 = TANK_TIER_1.get();
        tankTier2 = TANK_TIER_2.get();
        tankTier3 = TANK_TIER_3.get();
        tankTier4 = TANK_TIER_4.get();
        tankTier5 = TANK_TIER_5.get();
        tankTier6 = TANK_TIER_6.get();
        tankTier7 = TANK_TIER_7.get();
        tankTier8 = TANK_TIER_8.get();
        advancedReplicator = ADVANCED_REPLICATOR.get();
    }
}

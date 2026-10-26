package org.mob.replication_addon_tiers;

import com.hrznstudio.titanium.module.BlockWithTile;
import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import com.hrznstudio.titanium.tab.TitaniumTab;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.mob.replication_addon_tiers.block.custom.disintegrator.*;
import org.mob.replication_addon_tiers.block.custom.matter_network_pipe.*;
import org.mob.replication_addon_tiers.block.custom.matter_tank.*;
import org.mob.replication_addon_tiers.block.custom.replicator.*;
import org.mob.replication_addon_tiers.block.entity.matter_tank.MatterTankTierBlockEntity;
import org.mob.replication_addon_tiers.item.custom.MemoryChipItemTier;

public class ReplicationAddonTiersRegistry {
    private static DeferredRegistryHelper HELPER = null;
    private static final TitaniumTab TAB = ReplicationAddonTiers.TAB;

    public static void init(DeferredRegistryHelper helper){
        HELPER = helper;
        Blocks.init();
        Items.init();
    }

    public static class Blocks {
        public static void init() {}

        public static final BlockWithTile MATTER_TANK_TIER_1 = HELPER.registerBlockWithTile("matter_tank_tier_1", () -> new MatterTankTierBlock(TankTier.TIER_1), TAB);
        public static final BlockWithTile MATTER_TANK_TIER_2 = HELPER.registerBlockWithTile("matter_tank_tier_2", () -> new MatterTankTierBlock(TankTier.TIER_2), TAB);
        public static final BlockWithTile MATTER_TANK_TIER_3 = HELPER.registerBlockWithTile("matter_tank_tier_3", () -> new MatterTankTierBlock(TankTier.TIER_3), TAB);
        public static final BlockWithTile MATTER_TANK_TIER_4 = HELPER.registerBlockWithTile("matter_tank_tier_4", () -> new MatterTankTierBlock(TankTier.TIER_4), TAB);
        public static final BlockWithTile MATTER_TANK_TIER_5 = HELPER.registerBlockWithTile("matter_tank_tier_5", () -> new MatterTankTierBlock(TankTier.TIER_5), TAB);
        public static final BlockWithTile MATTER_TANK_TIER_6 = HELPER.registerBlockWithTile("matter_tank_tier_6", () -> new MatterTankTierBlock(TankTier.TIER_6), TAB);
        public static final BlockWithTile MATTER_TANK_TIER_7 = HELPER.registerBlockWithTile("matter_tank_tier_7", () -> new MatterTankTierBlock(TankTier.TIER_7), TAB);
        public static final BlockWithTile MATTER_TANK_TIER_8 = HELPER.registerBlockWithTile("matter_tank_tier_8", () -> new MatterTankTierBlock(TankTier.TIER_8), TAB);

        @SuppressWarnings("unchecked")
        public static BlockEntityType<MatterTankTierBlockEntity> getMatterTankType(TankTier tier) {
            return (BlockEntityType<MatterTankTierBlockEntity>) switch (tier) {
                case TIER_1 -> MATTER_TANK_TIER_1.type().get();
                case TIER_2 -> MATTER_TANK_TIER_2.type().get();
                case TIER_3 -> MATTER_TANK_TIER_3.type().get();
                case TIER_4 -> MATTER_TANK_TIER_4.type().get();
                case TIER_5 -> MATTER_TANK_TIER_5.type().get();
                case TIER_6 -> MATTER_TANK_TIER_6.type().get();
                case TIER_7 -> MATTER_TANK_TIER_7.type().get();
                case TIER_8 -> MATTER_TANK_TIER_8.type().get();
            };
        }

        public static final BlockWithTile ADVANCED_REPLICATOR = HELPER.registerBlockWithTile("advanced_replicator", AdvancedReplicatorBlock::new, TAB);
        public static final BlockWithTile ELITE_REPLICATOR = HELPER.registerBlockWithTile("elite_replicator", EliteReplicatorBlock::new, TAB);

        public static final BlockWithTile ADVANCED_DISINTEGRATOR = HELPER.registerBlockWithTile("advanced_disintegrator", AdvancedDisintegratorBlock::new, TAB);
        public static final BlockWithTile ELITE_DISINTEGRATOR = HELPER.registerBlockWithTile("elite_disintegrator", EliteDisintegratorBlock::new, TAB);

        public static final BlockWithTile ADVANCED_MATTER_NETWORK_PIPE = HELPER.registerBlockWithTile("advanced_matter_network_pipe", AdvancedMatterPipeBlock::new, TAB);
        public static final BlockWithTile ELITE_MATTER_NETWORK_PIPE = HELPER.registerBlockWithTile("elite_matter_network_pipe", EliteMatterPipeBlock::new, TAB);
        public static final BlockWithTile ULTIMATE_MATTER_NETWORK_PIPE = HELPER.registerBlockWithTile("ultimate_matter_network_pipe", UltimateMatterPipeBlock::new, TAB);
    }

    public static class Items {
        public static void init() {}

        public static DeferredHolder<Item, Item> MEMORY_CHIP_TIER_1 = HELPER.registerGeneric(Registries.ITEM, "memory_chip_tier_1", () -> new MemoryChipItemTier(64));
        public static DeferredHolder<Item, Item> MEMORY_CHIP_TIER_2 = HELPER.registerGeneric(Registries.ITEM, "memory_chip_tier_2", () -> new MemoryChipItemTier(128));
        public static DeferredHolder<Item, Item> MEMORY_CHIP_TIER_3 = HELPER.registerGeneric(Registries.ITEM, "memory_chip_tier_3", () -> new MemoryChipItemTier(256));
    }
}

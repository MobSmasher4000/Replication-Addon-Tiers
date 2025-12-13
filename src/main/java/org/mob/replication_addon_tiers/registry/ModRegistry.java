package org.mob.replication_addon_tiers.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.mob.replication_addon_tiers.ReplicationAddonTiers;
import org.mob.replication_addon_tiers.block.*;
import org.mob.replication_addon_tiers.block.custom.*;
import org.mob.replication_addon_tiers.block.custom.matterTank.*;

public class ModRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ReplicationAddonTiers.MOD_ID);

    public static final DeferredBlock<MatterTankTier1Block> MATTER_TANK_TIER_1 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_1", MatterTankTier1Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_1_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_1);

    public static final DeferredBlock<MatterTankTier2Block> MATTER_TANK_TIER_2 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_2", MatterTankTier2Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_2_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_2);

    public static final DeferredBlock<MatterTankTier3Block> MATTER_TANK_TIER_3 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_3", MatterTankTier3Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_3_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_3);

    public static final DeferredBlock<MatterTankTier4Block> MATTER_TANK_TIER_4 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_4", MatterTankTier4Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_4_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_4);

    public static final DeferredBlock<AdvancedReplicatorBlock> ADVANCED_REPLICATOR = ReplicationAddonTiers.BLOCKS.register("advanced_replicator", AdvancedReplicatorBlock::new);
    public static final DeferredItem<BlockItem> ADVANCED_REPLICATOR_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(ADVANCED_REPLICATOR);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatterTankTier1BlockEntity>> MATTER_TANK_TIER_1_BE = BLOCK_ENTITIES.register("matter_tank_tier_1",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new MatterTankTier1BlockEntity(MATTER_TANK_TIER_1.get(), null, pos, state),
                        MATTER_TANK_TIER_1.get()
                ).build(null);
                return type;
            });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatterTankTier2BlockEntity>> MATTER_TANK_TIER_2_BE = BLOCK_ENTITIES.register("matter_tank_tier_2",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new MatterTankTier2BlockEntity(MATTER_TANK_TIER_2.get(), null, pos, state),
                        MATTER_TANK_TIER_2.get()
                ).build(null);
                return type;
            });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatterTankTier3BlockEntity>> MATTER_TANK_TIER_3_BE = BLOCK_ENTITIES.register("matter_tank_tier_3",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new MatterTankTier3BlockEntity(MATTER_TANK_TIER_3.get(), null, pos, state),
                        MATTER_TANK_TIER_3.get()
                ).build(null);
                return type;
            });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatterTankTier4BlockEntity>> MATTER_TANK_TIER_4_BE = BLOCK_ENTITIES.register("matter_tank_tier_4",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new MatterTankTier4BlockEntity(MATTER_TANK_TIER_4.get(), null, pos, state),
                        MATTER_TANK_TIER_4.get()
                ).build(null);
                return type;
            });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AdvancedReplicatorBlockEntity>> ADVANCED_REPLICATOR_BE = BLOCK_ENTITIES.register("advanced_replicator",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new AdvancedReplicatorBlockEntity(ADVANCED_REPLICATOR.get(), null, pos, state),
                        ADVANCED_REPLICATOR.get()
                ).build(null);
                return type;
            });


}

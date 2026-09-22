package org.mob.replication_addon_tiers.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.mob.replication_addon_tiers.ReplicationAddonTiers;
import org.mob.replication_addon_tiers.block.*;
import org.mob.replication_addon_tiers.block.custom.*;
import org.mob.replication_addon_tiers.block.custom.matterTank.*;
import org.mob.replication_addon_tiers.item.custom.*;

public class ModRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ReplicationAddonTiers.MOD_ID);

    // Matter Tanks
    public static final DeferredBlock<MatterTankTier1Block> MATTER_TANK_TIER_1 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_1", MatterTankTier1Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_1_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_1);

    public static final DeferredBlock<MatterTankTier2Block> MATTER_TANK_TIER_2 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_2", MatterTankTier2Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_2_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_2);

    public static final DeferredBlock<MatterTankTier3Block> MATTER_TANK_TIER_3 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_3", MatterTankTier3Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_3_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_3);

    public static final DeferredBlock<MatterTankTier4Block> MATTER_TANK_TIER_4 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_4", MatterTankTier4Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_4_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_4);

    public static final DeferredBlock<MatterTankTier5Block> MATTER_TANK_TIER_5 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_5", MatterTankTier5Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_5_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_5);

    public static final DeferredBlock<MatterTankTier6Block> MATTER_TANK_TIER_6 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_6", MatterTankTier6Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_6_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_6);

    public static final DeferredBlock<MatterTankTier7Block> MATTER_TANK_TIER_7 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_7", MatterTankTier7Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_7_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_7);

    public static final DeferredBlock<MatterTankTier8Block> MATTER_TANK_TIER_8 = ReplicationAddonTiers.BLOCKS.register("matter_tank_tier_8", MatterTankTier8Block::new);
    public static final DeferredItem<BlockItem> MATTER_TANK_TIER_8_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(MATTER_TANK_TIER_8);

    // Memory Chips
    public static final DeferredItem<Item> MEMORY_CHIP_TIER_1 = ReplicationAddonTiers.ITEMS.register("memory_chip_tier_1",
            MemoryChipItemTier1::new);
    public static final DeferredItem<Item> MEMORY_CHIP_TIER_2 = ReplicationAddonTiers.ITEMS.register("memory_chip_tier_2",
            MemoryChipItemTier2::new);
    public static final DeferredItem<Item> MEMORY_CHIP_TIER_3 = ReplicationAddonTiers.ITEMS.register("memory_chip_tier_3",
            MemoryChipItemTier3::new);


    // Replicator
    public static final DeferredBlock<AdvancedReplicatorBlock> ADVANCED_REPLICATOR = ReplicationAddonTiers.BLOCKS.register("advanced_replicator", AdvancedReplicatorBlock::new);
    public static final DeferredItem<BlockItem> ADVANCED_REPLICATOR_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(ADVANCED_REPLICATOR);

    public static final DeferredBlock<EliteReplicatorBlock> ELITE_REPLICATOR = ReplicationAddonTiers.BLOCKS.register("elite_replicator", EliteReplicatorBlock::new);
    public static final DeferredItem<BlockItem> ELITE_REPLICATOR_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(ELITE_REPLICATOR);

    public static final DeferredBlock<AdvancedDisintegratorBlock> ADVANCED_DISINTEGRATOR = ReplicationAddonTiers.BLOCKS.register("advanced_disintegrator", AdvancedDisintegratorBlock::new);
    public static final DeferredItem<BlockItem> ADVANCED_DISINTEGRATOR_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(ADVANCED_DISINTEGRATOR);

    public static final DeferredBlock<EliteDisintegratorBlock> ELITE_DISINTEGRATOR = ReplicationAddonTiers.BLOCKS.register("elite_disintegrator", EliteDisintegratorBlock::new);
    public static final DeferredItem<BlockItem> ELITE_DISINTEGRATOR_ITEM = ReplicationAddonTiers.ITEMS.registerSimpleBlockItem(ELITE_DISINTEGRATOR);

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

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatterTankTier5BlockEntity>> MATTER_TANK_TIER_5_BE = BLOCK_ENTITIES.register("matter_tank_tier_5",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new MatterTankTier5BlockEntity(MATTER_TANK_TIER_5.get(), null, pos, state),
                        MATTER_TANK_TIER_5.get()
                ).build(null);
                return type;
            });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatterTankTier6BlockEntity>> MATTER_TANK_TIER_6_BE = BLOCK_ENTITIES.register("matter_tank_tier_6",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new MatterTankTier6BlockEntity(MATTER_TANK_TIER_6.get(), null, pos, state),
                        MATTER_TANK_TIER_6.get()
                ).build(null);
                return type;
            });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatterTankTier7BlockEntity>> MATTER_TANK_TIER_7_BE = BLOCK_ENTITIES.register("matter_tank_tier_7",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new MatterTankTier7BlockEntity(MATTER_TANK_TIER_7.get(), null, pos, state),
                        MATTER_TANK_TIER_7.get()
                ).build(null);
                return type;
            });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatterTankTier8BlockEntity>> MATTER_TANK_TIER_8_BE = BLOCK_ENTITIES.register("matter_tank_tier_8",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new MatterTankTier8BlockEntity(MATTER_TANK_TIER_8.get(), null, pos, state),
                        MATTER_TANK_TIER_8.get()
                ).build(null);
                return type;
            });


    // Replicator
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AdvancedReplicatorBlockEntity>> ADVANCED_REPLICATOR_BE = BLOCK_ENTITIES.register("advanced_replicator",
            () -> {
                BlockEntityType<AdvancedReplicatorBlockEntity>[] typeRef = new BlockEntityType[1];
                typeRef[0] = BlockEntityType.Builder.of(
                        (pos, state) -> new AdvancedReplicatorBlockEntity(ADVANCED_REPLICATOR.get(), typeRef[0], pos, state),
                        ADVANCED_REPLICATOR.get()
                ).build(null);
                return typeRef[0];
            });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EliteReplicatorBlockEntity>> ELITE_REPLICATOR_BE = BLOCK_ENTITIES.register("elite_replicator",
            () -> {
                BlockEntityType<EliteReplicatorBlockEntity>[] typeRef = new BlockEntityType[1];
                typeRef[0] = BlockEntityType.Builder.of(
                        (pos, state) -> new EliteReplicatorBlockEntity(ELITE_REPLICATOR.get(), typeRef[0], pos, state),
                        ELITE_REPLICATOR.get()
                ).build(null);
                return typeRef[0];
            });

    // Disintegrator
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AdvancedDisintegratorBlockEntity>> ADVANCED_DISINTEGRATOR_BE = BLOCK_ENTITIES.register("advanced_disintegrator",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new AdvancedDisintegratorBlockEntity(ADVANCED_DISINTEGRATOR.get(), null, pos, state),
                        ADVANCED_DISINTEGRATOR.get()
                ).build(null);
                return type;
            });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EliteDisintegratorBlockEntity>> ELITE_DISINTEGRATOR_BE = BLOCK_ENTITIES.register("elite_disintegrator",
            () -> {
                var type = BlockEntityType.Builder.of(
                        (pos, state) -> new EliteDisintegratorBlockEntity(ELITE_DISINTEGRATOR.get(), null, pos, state),
                        ELITE_DISINTEGRATOR.get()
                ).build(null);
                return type;
            });
}

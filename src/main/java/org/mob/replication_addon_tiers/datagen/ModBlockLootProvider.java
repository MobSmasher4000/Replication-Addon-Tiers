package org.mob.replication_addon_tiers.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.mob.replication_addon_tiers.ReplicationAddonTiers;
import org.mob.replication_addon_tiers.registry.ModRegistry;

import java.util.Set;

public class ModBlockLootProvider extends BlockLootSubProvider {
    protected ModBlockLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModRegistry.MATTER_TANK_TIER_1.get());
        dropSelf(ModRegistry.MATTER_TANK_TIER_2.get());
        dropSelf(ModRegistry.MATTER_TANK_TIER_3.get());
        dropSelf(ModRegistry.MATTER_TANK_TIER_4.get());

    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ReplicationAddonTiers.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}

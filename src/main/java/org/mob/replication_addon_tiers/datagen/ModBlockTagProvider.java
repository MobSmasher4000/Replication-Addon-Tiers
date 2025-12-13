package org.mob.replication_addon_tiers.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.mob.replication_addon_tiers.ReplicationAddonTiers;
import org.mob.replication_addon_tiers.registry.ModRegistry;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ReplicationAddonTiers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModRegistry.MATTER_TANK_TIER_1.get())
                .add(ModRegistry.MATTER_TANK_TIER_2.get())
                .add(ModRegistry.MATTER_TANK_TIER_3.get())
                .add(ModRegistry.MATTER_TANK_TIER_4.get())
                .add(ModRegistry.ADVANCED_REPLICATOR.get())
        ;
    }
}

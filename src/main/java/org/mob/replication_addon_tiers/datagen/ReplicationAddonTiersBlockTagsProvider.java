package org.mob.replication_addon_tiers.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ReplicationAddonTiersBlockTagsProvider extends BlockTagsProvider {

    private final List<Block> blocks;

    public ReplicationAddonTiersBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, List<Block> blocks) {
        super(output, lookupProvider, modId);
        this.blocks = blocks;
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(blocks.toArray(Block[]::new));
    }
}
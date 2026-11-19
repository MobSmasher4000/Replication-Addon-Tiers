package org.mob.replication_addon_tiers.registry;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.mob.replication_addon_tiers.ReplicationAddonTiers;
import org.mob.replication_addon_tiers.recipe.ChipMergeRecipe;

public final class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ReplicationAddonTiers.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ChipMergeRecipe>> CHIP_MERGE =
            SERIALIZERS.register("chip_merge", () -> new RecipeSerializer<>() {
                @Override
                public MapCodec<ChipMergeRecipe> codec() {
                    return ChipMergeRecipe.CODEC;
                }

                @Override
                public StreamCodec<RegistryFriendlyByteBuf, ChipMergeRecipe> streamCodec() {
                    return ChipMergeRecipe.STREAM_CODEC;
                }
            });

    private ModRecipes() {
    }
}
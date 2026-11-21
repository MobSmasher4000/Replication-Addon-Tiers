package org.mob.replication_addon_tiers.recipe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.buuz135.replication.ReplicationAttachments;
import com.buuz135.replication.api.pattern.IMatterPatternHolder;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.mob.replication_addon_tiers.registry.ModRecipes;

/**
 * Shaped recipe that merges the patterns of every memory chip used as ingredient into the result chip.
 * Duplicates are merged (the highest completion is kept). The recipe does not match if the merged
 * patterns would not fit in the result chip, so no pattern is ever lost.
 */
public class ChipMergeRecipe implements CraftingRecipe {

    // Same JSON and network format as a vanilla shaped recipe, only the "type" changes.
    public static final MapCodec<ChipMergeRecipe> CODEC =
            RecipeSerializer.SHAPED_RECIPE.codec().xmap(ChipMergeRecipe::new, ChipMergeRecipe::delegate);
    public static final StreamCodec<RegistryFriendlyByteBuf, ChipMergeRecipe> STREAM_CODEC =
            RecipeSerializer.SHAPED_RECIPE.streamCodec().map(ChipMergeRecipe::new, ChipMergeRecipe::delegate);

    private final ShapedRecipe delegate;

    public ChipMergeRecipe(ShapedRecipe delegate) {
        this.delegate = delegate;
    }

    public ShapedRecipe delegate() {
        return delegate;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (!delegate.matches(input, level)) return false;
        ItemStack result = delegate.getResultItem(level.registryAccess());
        return mergePatterns(input).size() <= patternSlots(result);
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack result = delegate.assemble(input, registries);
        List<CompoundTag> patterns = mergePatterns(input);
        if (!patterns.isEmpty()) {
            CompoundTag tag = new CompoundTag();
            for (int i = 0; i < patterns.size(); i++) {
                tag.put(String.valueOf(i), patterns.get(i));
            }
            result.set(ReplicationAttachments.CHIP_PATTERNS, tag);
        }
        return result;
    }

    // Works on the raw NBT of each pattern ({Stack: ..., Completion: ...}) so no Level is needed.
    private static List<CompoundTag> mergePatterns(CraftingInput input) {
        Map<CompoundTag, CompoundTag> merged = new LinkedHashMap<>();
        for (ItemStack stack : input.items()) {
            CompoundTag patterns = stack.get(ReplicationAttachments.CHIP_PATTERNS);
            if (patterns == null) continue;
            List<String> keys = patterns.getAllKeys().stream()
                    .sorted(Comparator.comparingInt(ChipMergeRecipe::slotIndex))
                    .toList();
            for (String key : keys) {
                CompoundTag pattern = patterns.getCompound(key);
                CompoundTag item = pattern.getCompound("Stack").copy();
                if (item.isEmpty()) continue;
                item.remove("count");
                merged.merge(item, pattern.copy(),
                        (a, b) -> a.getFloat("Completion") >= b.getFloat("Completion") ? a : b);
            }
        }
        return new ArrayList<>(merged.values());
    }

    private static int slotIndex(String key) {
        try {
            return Integer.parseInt(key);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }

    @SuppressWarnings("unchecked")
    private static int patternSlots(ItemStack stack) {
        if (stack.getItem() instanceof IMatterPatternHolder<?> holder) {
            return ((IMatterPatternHolder<ItemStack>) holder).getPatternSlots(stack);
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return delegate.canCraftInDimensions(width, height);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return delegate.getResultItem(registries);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CHIP_MERGE.get();
    }

    @Override
    public CraftingBookCategory category() {
        return delegate.category();
    }

    @Override
    public String getGroup() {
        return delegate.getGroup();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return delegate.getIngredients();
    }

    @Override
    public boolean showNotification() {
        return delegate.showNotification();
    }

    @Override
    public boolean isIncomplete() {
        return delegate.isIncomplete();
    }

}
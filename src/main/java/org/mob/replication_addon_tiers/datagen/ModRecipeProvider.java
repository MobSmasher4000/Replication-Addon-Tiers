package org.mob.replication_addon_tiers.datagen;

import com.buuz135.replication.ReplicationRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.mob.replication_addon_tiers.registry.ModRegistry;

import java.util.concurrent.CompletableFuture;


public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC , ModRegistry.MATTER_TANK_TIER_1.get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ReplicationRegistry.Blocks.MATTER_TANK.asItem())
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_tank", has(ReplicationRegistry.Blocks.MATTER_TANK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC , ModRegistry.MATTER_TANK_TIER_2.get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ModRegistry.MATTER_TANK_TIER_1.get())
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_tank", has(ReplicationRegistry.Blocks.MATTER_TANK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC , ModRegistry.MATTER_TANK_TIER_3.get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ModRegistry.MATTER_TANK_TIER_2.get())
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_tank", has(ReplicationRegistry.Blocks.MATTER_TANK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC , ModRegistry.MATTER_TANK_TIER_4.get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ModRegistry.MATTER_TANK_TIER_3.get())
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_tank", has(ReplicationRegistry.Blocks.MATTER_TANK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC , ModRegistry.MATTER_TANK_TIER_5.get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ModRegistry.MATTER_TANK_TIER_4.get())
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_tank", has(ReplicationRegistry.Blocks.MATTER_TANK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC , ModRegistry.MATTER_TANK_TIER_6.get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ModRegistry.MATTER_TANK_TIER_5.get())
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_tank", has(ReplicationRegistry.Blocks.MATTER_TANK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC , ModRegistry.MATTER_TANK_TIER_7.get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ModRegistry.MATTER_TANK_TIER_6.get())
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_tank", has(ReplicationRegistry.Blocks.MATTER_TANK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC , ModRegistry.MATTER_TANK_TIER_8.get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ModRegistry.MATTER_TANK_TIER_7.get())
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_tank", has(ReplicationRegistry.Blocks.MATTER_TANK))
                .save(recipeOutput);



        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModRegistry.ADVANCED_REPLICATOR.get())
                .pattern("IP ")
                .pattern("IRM")
                .pattern("III")
                .define('P', Items.PISTON)
                .define('I', (ItemLike)ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('R', ReplicationRegistry.Blocks.REPLICATOR)
                .define('M', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_replicator", has(ReplicationRegistry.Blocks.REPLICATOR))
                .save(recipeOutput);

    }

}

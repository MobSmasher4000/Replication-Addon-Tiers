package org.mob.replication_addon_tiers.datagen;

import com.buuz135.replication.ReplicationRegistry;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import org.mob.replication_addon_tiers.ReplicationAddonTiersRegistry;

import java.util.concurrent.CompletableFuture;

public class ReplicationAddonTiersRecipesProvider extends RecipeProvider {

    public ReplicationAddonTiersRecipesProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_1.block().get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ReplicationRegistry.Blocks.MATTER_TANK.asItem())
                .define('G', Items.GLASS)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_2.block().get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_1.block().get())
                .define('G', Items.GLASS)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_3.block().get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_2.block().get())
                .define('G', Items.GLASS)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_4.block().get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_3.block().get())
                .define('G', Items.GLASS)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_5.block().get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_4.block().get())
                .define('G', Items.GLASS)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_6.block().get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_5.block().get())
                .define('G', Items.GLASS)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_7.block().get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_6.block().get())
                .define('G', Items.GLASS)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_8.block().get())
                .pattern("RGR")
                .pattern("GTG")
                .pattern("RGR")
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('T', ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_7.block().get())
                .define('G', Items.GLASS)
                .save(output);


        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.ADVANCED_REPLICATOR.block().get())
                .pattern("IP ")
                .pattern("IRM")
                .pattern("III")
                .define('P', Items.PISTON)
                .define('I', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('R', ReplicationRegistry.Blocks.REPLICATOR)
                .define('M', Items.IRON_INGOT)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.ELITE_REPLICATOR.block().get())
                .pattern("IP ")
                .pattern("IRM")
                .pattern("III")
                .define('P', Items.PISTON)
                .define('I', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('R', ReplicationAddonTiersRegistry.Blocks.ADVANCED_REPLICATOR.block().get())
                .define('M', Items.IRON_INGOT)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.ADVANCED_DISINTEGRATOR.block().get())
                .pattern("ITG")
                .pattern("ITG")
                .pattern("III")
                .define('T', ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_1.block().get())
                .define('I', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('G', ReplicationRegistry.Blocks.DISINTEGRATOR)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.ELITE_DISINTEGRATOR.block().get())
                .pattern("ITG")
                .pattern("ITG")
                .pattern("III")
                .define('T', ReplicationAddonTiersRegistry.Blocks.MATTER_TANK_TIER_2.block().get())
                .define('I', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('G', ReplicationAddonTiersRegistry.Blocks.ADVANCED_DISINTEGRATOR.block().get())
                .save(output);


        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Items.MEMORY_CHIP_TIER_1.get())
                .pattern("CRC")
                .pattern("RIR")
                .pattern("CRC")
                .define('C', ReplicationRegistry.Items.MEMORY_CHIP.get())
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('I', ReplicationRegistry.Blocks.IDENTIFICATION_CHAMBER.block().get())
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Items.MEMORY_CHIP_TIER_2.get())
                .pattern("CRC")
                .pattern("RIR")
                .pattern("CRC")
                .define('C', ReplicationAddonTiersRegistry.Items.MEMORY_CHIP_TIER_1.get())
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('I', ReplicationRegistry.Blocks.IDENTIFICATION_CHAMBER.block().get())
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Items.MEMORY_CHIP_TIER_3.get())
                .pattern("CRC")
                .pattern("RIR")
                .pattern("CRC")
                .define('C', ReplicationAddonTiersRegistry.Items.MEMORY_CHIP_TIER_2.get())
                .define('R', ReplicationRegistry.Items.REPLICA_INGOT.get())
                .define('I', ReplicationRegistry.Blocks.IDENTIFICATION_CHAMBER.block().get())
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.ADVANCED_MATTER_NETWORK_PIPE.block().get(), 8)
                .pattern("PPP")
                .pattern("PIP")
                .pattern("PPP")
                .define('P', ReplicationRegistry.Blocks.MATTER_NETWORK_PIPE.block().get())
                .define('I', Items.DIAMOND)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.ELITE_MATTER_NETWORK_PIPE.block().get(), 8)
                .pattern("PPP")
                .pattern("PIP")
                .pattern("PPP")
                .define('P', ReplicationAddonTiersRegistry.Blocks.ADVANCED_MATTER_NETWORK_PIPE.block().get())
                .define('I', Items.EMERALD)
                .save(output);

        TitaniumShapedRecipeBuilder.shapedRecipe(ReplicationAddonTiersRegistry.Blocks.ULTIMATE_MATTER_NETWORK_PIPE.block().get(), 8)
                .pattern("PPP")
                .pattern("PIP")
                .pattern("PPP")
                .define('P', ReplicationAddonTiersRegistry.Blocks.ELITE_MATTER_NETWORK_PIPE.block().get())
                .define('I', Items.NETHERITE_INGOT)
                .save(output);
    }

    public static class Runner extends RecipeProvider.Runner {


        public Runner(DataGenerator generator, CompletableFuture<HolderLookup.Provider> registries) {
            super(generator.getPackOutput(), registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ReplicationAddonTiersRecipesProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Replication Addon Tiers Recipes";
        }
    }
}
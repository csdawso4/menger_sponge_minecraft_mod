package com.craigtec.datagen;

import com.craigtec.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                SimpleCookingRecipeBuilder.smelting(
                                Ingredient.of(ModBlocks.WET_MENGER_SPONGE_BLOCK),
                                RecipeCategory.BUILDING_BLOCKS,
                                CookingBookCategory.BLOCKS,
                                ModBlocks.MENGER_SPONGE_BLOCK,
                                0.15F,
                                200
                        )
                        .unlockedBy(getHasName(ModBlocks.WET_MENGER_SPONGE_BLOCK), this.has(ModBlocks.WET_MENGER_SPONGE_BLOCK))
                        .save(this.output);
            }
        };
    }

    @Override
    public String getName() {
        return "Menger Block Recipes";
    }
}

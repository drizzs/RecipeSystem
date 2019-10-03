package com.teamacronymcoders.recipesystem.recipe.handler;


import com.teamacronymcoders.recipesystem.recipe.Recipe;
import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.List;

public interface IRecipeHandler {
    boolean handleRecipe(RecipeContainer recipeContainer, @Nullable PlayerEntity entityPlayer);

    void reloadRecipes(List<Recipe> recipeList);
}

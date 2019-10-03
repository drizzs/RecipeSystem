package com.teamacronymcoders.recipesystem.recipe.output;

import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;

public interface IOutput {
    boolean canOutput(RecipeContainer recipeContainer);

    void output(RecipeContainer recipeContainer);
}

package com.teamacronymcoders.base.recipesystem.output;

import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;

public interface IOutput {
    boolean canOutput(RecipeContainer recipeContainer);

    void output(RecipeContainer recipeContainer);
}

package com.teamacronymcoders.base.recipesystem.input;

import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;

public interface IInput {
    boolean isMatched(RecipeContainer recipeContainer);

    void consume(RecipeContainer recipeContainer);
}

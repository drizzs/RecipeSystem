package com.teamacronymcoders.recipesystem.recipe.loader;


import com.teamacronymcoders.recipesystem.recipe.Recipe;
import com.teamacronymcoders.recipesystem.recipe.source.IRecipeSource;

import java.util.List;

public interface ILoader {
    IRecipeSource getRecipeSource();

    List<Recipe> loadRecipes();
}

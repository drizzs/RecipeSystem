package com.teamacronymcoders.recipesystem.recipe;

import com.google.common.collect.ImmutableList;
import com.teamacronymcoders.recipesystem.recipe.type.RecipeType;

import java.util.List;

public class RecipeList {
    public final RecipeType type;
    public final ImmutableList<Recipe> recipes;

    public RecipeList(RecipeType type, List<Recipe> recipes) {
        this(type, ImmutableList.copyOf(recipes));
    }

    public RecipeList(RecipeType type, ImmutableList<Recipe> recipes) {
        this.type = type;
        this.recipes = recipes;
    }
}

package com.teamacronymcoders.recipesystem.recipe.type;

import com.google.common.collect.Lists;
import com.teamacronymcoders.recipesystem.recipe.Recipes;
import com.teamacronymcoders.recipesystem.recipe.handler.DefaultRecipeHandler;
import com.teamacronymcoders.recipesystem.recipe.handler.IRecipeHandler;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RecipeType {
    public final String name;
    public final List<IRecipeHandler> recipeHandlers;

    public RecipeType(ResourceLocation name) {
        this.name = name.toString();
        recipeHandlers = Lists.newArrayList();
    }

    public IRecipeHandler createHandler() {
        return new DefaultRecipeHandler(Recipes.getRecipesFor(this));
    }

    public List<IRecipeHandler> getRecipeHandlers() {
        return this.recipeHandlers;
    }
}

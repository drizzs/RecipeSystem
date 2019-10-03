package com.teamacronymcoders.recipesystem.recipe.type;

import com.teamacronymcoders.recipesystem.recipe.RecipeSystem;
import com.teamacronymcoders.recipesystem.recipe.handler.ClickedRecipeHandler;
import com.teamacronymcoders.recipesystem.recipe.handler.IRecipeHandler;
import net.minecraft.util.ResourceLocation;

public class ClickRecipeType extends RecipeType {
    public ClickRecipeType(ResourceLocation name) {
        super(name);
    }

    @Override
    public IRecipeHandler createHandler() {
        return new ClickedRecipeHandler(RecipeSystem.getRecipesFor(this));
    }
}

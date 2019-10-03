package com.teamacronymcoders.recipesystem.recipe.condition;

import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;

public class VillageCondition implements ICondition {
    private final boolean inVillage;

    public VillageCondition(boolean inVillage) {
        this.inVillage = inVillage;
    }

    @Override
    public boolean isMet(RecipeContainer recipeContainer, @Nullable PlayerEntity entityPlayer) {
        return locatedInVillage(recipeContainer) == inVillage;
    }

    @SuppressWarnings("ConstantConditions")
    private boolean locatedInVillage(RecipeContainer recipeContainer) {
        return recipeContainer.getWorld().getVillageCollection().getNearestVillage(recipeContainer.getPos(), 32) != null;
    }
}

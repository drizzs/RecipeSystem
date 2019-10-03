package com.teamacronymcoders.recipesystem.recipe.condition;

import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;

public interface ICondition {
    boolean isMet(RecipeContainer recipeContainer, @Nullable PlayerEntity entityPlayer);
}

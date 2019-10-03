package com.teamacronymcoders.base.recipesystem.condition;

import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;

public interface ICondition {
    boolean isMet(RecipeContainer recipeContainer, @Nullable EntityPlayer entityPlayer);
}

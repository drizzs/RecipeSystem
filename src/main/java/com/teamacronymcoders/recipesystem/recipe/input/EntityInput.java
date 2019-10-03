package com.teamacronymcoders.base.recipesystem.input;

import com.google.gson.annotations.JsonAdapter;
import com.teamacronymcoders.base.json.deserializer.EntityClassDeserializer;
import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;
import net.minecraft.entity.Entity;

public class EntityInput implements IInput {
    @JsonAdapter(value = EntityClassDeserializer.class)
    private final Class<? extends Entity> entity;

    public EntityInput(Class<? extends Entity> entity) {
        this.entity = entity;
    }

    @Override
    public boolean isMatched(RecipeContainer recipeContainer) {
        return recipeContainer.getRecipeHolder().getClass().isAssignableFrom(entity);
    }

    @Override
    public void consume(RecipeContainer recipeContainer) {
        if (recipeContainer.getRecipeHolder().getClass().isAssignableFrom(entity)) {
            ((Entity) recipeContainer.getRecipeHolder()).setDead();
        }
    }
}

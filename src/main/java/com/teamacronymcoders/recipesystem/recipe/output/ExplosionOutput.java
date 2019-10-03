package com.teamacronymcoders.recipesystem.recipe.output;

import com.google.gson.annotations.JsonAdapter;
import com.teamacronymcoders.recipesystem.json.deserializer.BlockPosDeserializer;
import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;

public class ExplosionOutput implements IOutput {
    @JsonAdapter(BlockPosDeserializer.class)
    private final BlockPos offset;
    private final float strength;
    private final boolean causesFire;

    public ExplosionOutput(BlockPos offset, float strength, boolean damageTerrain, boolean causesFire) {
        this.offset = offset;
        this.strength = strength;
        this.causesFire = causesFire;
    }

    @Override
    public boolean canOutput(RecipeContainer recipeContainer) {
        return true;
    }

    @Override
    public void output(RecipeContainer recipeContainer) {
        BlockPos explosionPos = offset == null ? recipeContainer.getPos() : recipeContainer.getPos().add(offset);
        recipeContainer.getWorld().createExplosion(null, explosionPos.getX(), explosionPos.getY(), explosionPos.getZ(),
                strength, causesFire, Explosion.Mode.DESTROY);
    }
}

package com.teamacronymcoders.recipesystem.recipe.input;

import com.google.gson.annotations.JsonAdapter;
import com.teamacronymcoders.recipesystem.block.BlockStateMatcher;
import com.teamacronymcoders.recipesystem.json.deserializer.BlockPosDeserializer;
import com.teamacronymcoders.recipesystem.json.deserializer.BlockStateMatcherDeserializer;
import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;
import net.minecraft.util.math.BlockPos;

public class BlockStateInput implements IInput {
    @JsonAdapter(value = BlockStateMatcherDeserializer.class)
    private final BlockStateMatcher blockState;
    @JsonAdapter(value = BlockPosDeserializer.class)
    private final BlockPos offset;

    public BlockStateInput(BlockStateMatcher blockStateMatcher, BlockPos offset) {
        this.blockState = blockStateMatcher;
        this.offset = offset;
    }

    @Override
    public boolean isMatched(RecipeContainer recipeContainer) {
        return blockState.matches(recipeContainer.getWorld().getBlockState(handlePos(recipeContainer)));
    }

    @Override
    public void consume(RecipeContainer recipeContainer) {
        recipeContainer.getWorld().isAirBlock(handlePos(recipeContainer));
    }

    private BlockPos handlePos(RecipeContainer recipeContainer) {
        return offset != null ? recipeContainer.getPos().add(offset) : recipeContainer.getPos();
    }
}

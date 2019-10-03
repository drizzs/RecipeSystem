package com.teamacronymcoders.recipesystem.recipe.output;

import com.google.gson.annotations.JsonAdapter;
import com.teamacronymcoders.recipesystem.json.deserializer.BlockPosDeserializer;
import com.teamacronymcoders.recipesystem.json.deserializer.BlockStateDeserializer;
import com.teamacronymcoders.recipesystem.json.deserializer.NBTDeserializer;
import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class BlockStateOutput implements IOutput {
    @JsonAdapter(value = BlockStateDeserializer.class)
    private final BlockState blockState;
    @JsonAdapter(value = BlockPosDeserializer.class)
    private final BlockPos offset;
    @JsonAdapter(value = NBTDeserializer.class)
    private final CompoundNBT tileEntityNBT;

    public BlockStateOutput(BlockState blockState, BlockPos offset, CompoundNBT tileEntityNBT) {
        this.blockState = blockState;
        this.offset = offset;
        this.tileEntityNBT = tileEntityNBT;
    }

    @Override
    public boolean canOutput(RecipeContainer recipeContainer) {
        return true;
    }

    @Override
    public void output(RecipeContainer recipeContainer) {
        BlockPos pos = offset != null ? recipeContainer.getPos().add(offset) : recipeContainer.getPos();
        recipeContainer.getWorld().setBlockState(pos, blockState, 3);
        if (tileEntityNBT != null && !tileEntityNBT.isEmpty()) {
            TileEntity tileEntity = recipeContainer.getWorld().getTileEntity(pos);
            if (tileEntity != null) {
                CompoundNBT currentNBT = tileEntity.write(new CompoundNBT());
                CompoundNBT originalNBT = currentNBT.copy();
                currentNBT.merge(tileEntityNBT.copy());
                currentNBT.putInt("x", pos.getX());
                currentNBT.putInt("y", pos.getY());
                currentNBT.putInt("z", pos.getZ());

                if (!currentNBT.equals(originalNBT)) {
                    tileEntity.read(currentNBT);
                    tileEntity.markDirty();
                }
            }
        }
    }
}

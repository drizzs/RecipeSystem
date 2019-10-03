package com.teamacronymcoders.recipesystem.recipe.command;

import com.teamacronymcoders.recipesystem.recipe.RecipeContainer;
import net.minecraft.command.ICommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RecipeSystemCommandSender implements ICommandSource {
    private final RecipeContainer recipeContainer;

    public RecipeSystemCommandSender(RecipeContainer recipeContainer) {
        this.recipeContainer = recipeContainer;
    }

    @Override
    @Nonnull
    public String getName() {
        return "RecipeSystem";
    }

    @Override
    public boolean canUseCommand(int permLevel, @Nonnull String commandName) {
        return true;
    }

    @Override
    @Nonnull
    public World getEntityWorld() {
        return recipeContainer.getWorld();
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return recipeContainer.getWorld().getServer();
    }
}

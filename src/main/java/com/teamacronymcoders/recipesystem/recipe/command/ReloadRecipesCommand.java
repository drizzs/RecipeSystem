package com.teamacronymcoders.recipesystem.recipe.command;

import com.teamacronymcoders.recipesystem.recipe.RecipeSystem;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class ReloadRecipesCommand extends CommandSource {
    @Override
    @Nonnull
    public String getName() {
        return "reload";
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public String getUsage(ICommandSender sender) {
        return "Reloads the Recipe System";
    }

    @Override
    @ParametersAreNonnullByDefault
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        RecipeSystem.reloadRecipe();
    }
}

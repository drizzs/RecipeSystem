package com.teamacronymcoders.recipesystem.recipe.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.teamacronymcoders.recipesystem.recipe.Recipe;
import com.teamacronymcoders.recipesystem.recipe.Recipes;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class ReloadRecipesCommand {

    public static LiteralArgumentBuilder<CommandSource> create() {
        return LiteralArgumentBuilder.<CommandSource>literal("Reload")
                .then(getUsage())
                .executes(ReloadRecipesCommand::reloadRecipes);
    }

    private static LiteralArgumentBuilder<CommandSource> getUsage() {
        return Commands.literal("Reloads the Recipe System");
    }

    private static int reloadRecipes(CommandContext<CommandSource> context) {
        Recipes.reloadRecipe();
        return 1;
    }

}

package com.teamacronymcoders.recipesystem.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamacronymcoders.recipesystem.event.BaseRegistryEvent;
import com.teamacronymcoders.recipesystem.recipe.command.ReloadRecipesCommand;
import com.teamacronymcoders.recipesystem.recipe.loader.ILoader;
import com.teamacronymcoders.recipesystem.recipe.type.RecipeType;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Recipes {
    private final static Map<String, RecipeType> recipeTypes = Maps.newHashMap();
    private final static Map<RecipeType, List<Recipe>> recipeLists = Maps.newHashMap();
    private final static Map<String, ILoader> loaders = Maps.newHashMap();

    private final static CommandSubBase recipeCommand = new CommandSubBase("recipes");

    public static void loadRecipeTypes() {
        BaseRegistryEvent<RecipeType> recipeTypeEvent = new BaseRegistryEvent<>(RecipeType.class);
        MinecraftForge.EVENT_BUS.post(recipeTypeEvent);
        recipeTypes.putAll(recipeTypeEvent.getEntries());

        BaseRegistryEvent<ILoader> loaderEvent = new BaseRegistryEvent<>(ILoader.class);
        MinecraftForge.EVENT_BUS.post(loaderEvent);
        loaders.putAll(loaderEvent.getEntries());
    }

    public static void loadRecipes(boolean reload) {
        loaders.values().stream()
                .filter(loader -> !reload || loader.getRecipeSource().canReload())
                .map(ILoader::loadRecipes)
                .flatMap(List::stream)
                .forEach(recipe -> {
                    recipeLists.putIfAbsent(recipe.type, Lists.newArrayList());
                    recipeLists.get(recipe.type).add(recipe);
                });

        recipeTypes.values().parallelStream()
                .forEach(Recipes::handleRecipeType);
    }

    private static void handleRecipeType(RecipeType recipeType) {
        List<Recipe> recipes = getRecipesFor(recipeType);
        recipes.sort(Comparator.comparingInt(recipe -> recipe.priority));
        recipeType.recipeHandlers.forEach(recipeHandler -> recipeHandler.reloadRecipes(recipes));
    }

    public static void reloadRecipe() {
        for (List<Recipe> recipes : recipeLists.values()) {
            recipes.removeIf(recipe -> recipe.source.canReload());
        }
        loadRecipes(true);
    }

    public static RecipeType getRecipeType(String typeName) {
        return recipeTypes.get(typeName);
    }

    public static List<Recipe> getRecipesFor(RecipeType recipeType) {
        return recipeLists.getOrDefault(recipeType, new ArrayList<>());
    }

    public static void setup() {
        Base.instance.getBaseCommand().addSubcommand(recipeCommand);
        recipeCommand.addSubcommand(new ReloadRecipesCommand());
    }
}

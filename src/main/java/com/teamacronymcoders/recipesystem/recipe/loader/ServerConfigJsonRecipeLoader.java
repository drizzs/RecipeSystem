package com.teamacronymcoders.recipesystem.recipe.loader;

import com.google.common.collect.Lists;
import com.teamacronymcoders.recipesystem.recipe.Recipe;
import com.teamacronymcoders.recipesystem.recipe.source.IRecipeSource;
import com.teamacronymcoders.recipesystem.recipe.source.RecipeSource;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class ServerConfigJsonRecipeLoader extends JsonRecipeLoader {
    private static ServerConfigJsonRecipeLoader instance;
    private final IRecipeSource RECIPE_SOURCE = new RecipeSource("server", true);

    public static ServerConfigJsonRecipeLoader getInstance() {
        if (instance == null) {
            instance = new ServerConfigJsonRecipeLoader();
        }
        return instance;
    }

    @Override
    public IRecipeSource getRecipeSource() {
        return RECIPE_SOURCE;
    }

    @Override
    public List<Recipe> loadRecipes() {
        List<Recipe> recipes = Lists.newArrayList();
        Path resourcesPath = Paths.get(Base.instance.getMinecraftFolder().getPath(), "resources");

        File resourcesFolder = resourcesPath.toFile();
        if (resourcesFolder.exists()) {
            File[] modFolders = resourcesFolder.listFiles(File::isDirectory);
            if (modFolders != null) {
                for (File modFolder : modFolders) {
                    File recipesFolder = new File(modFolder, "base/recipe_system");
                    if (recipesFolder.exists()) {
                        Path root = Paths.get(recipesFolder.getPath());
                        File[] recipeJsons = recipesFolder.listFiles(file ->
                                FilenameUtils.getExtension(file.getName()).equals("json"));
                        if (recipeJsons != null) {
                            for (File recipeJson : recipeJsons) {
                                Path file = Paths.get(recipeJson.getPath());
                                Optional.ofNullable(this.loadRecipe(root, file))
                                        .ifPresent(recipes::add);
                            }
                        }
                    }
                }
            }
        }
        return recipes;
    }
}

package com.teamacronymcoders.recipesystem.recipe.loader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import com.teamacronymcoders.recipesystem.RecipeSystem;
import com.teamacronymcoders.recipesystem.json.JsonContext;
import com.teamacronymcoders.recipesystem.json.factory.IObjectFactory;
import com.teamacronymcoders.recipesystem.recipe.Recipe;
import com.teamacronymcoders.recipesystem.recipe.Recipes;
import com.teamacronymcoders.recipesystem.recipe.condition.ICondition;
import com.teamacronymcoders.recipesystem.recipe.event.RegisterRecipeFactoriesEvent;
import com.teamacronymcoders.recipesystem.recipe.input.IInput;
import com.teamacronymcoders.recipesystem.recipe.output.IOutput;
import com.teamacronymcoders.recipesystem.recipe.type.RecipeType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class JsonRecipeLoader implements ILoader {
    private static boolean loadedFactories = false;

    private static final Map<String, IObjectFactory<? extends IInput>> inputFactories = Maps.newHashMap();
    private static final Map<String, IObjectFactory<? extends IOutput>> outputFactories = Maps.newHashMap();
    private static final Map<String, IObjectFactory<? extends ICondition>> conditionFactories = Maps.newHashMap();
    private final Gson GSON = new Gson();

    protected JsonRecipeLoader() {
        loadFactories();
    }

    private void loadFactories() {
        if (!loadedFactories) {
            RegisterRecipeFactoriesEvent<IInput> inputEvent = new RegisterRecipeFactoriesEvent<>(IInput.class);
            MinecraftForge.EVENT_BUS.post(inputEvent);
            inputFactories.putAll(inputEvent.getFactories());

            RegisterRecipeFactoriesEvent<IOutput> outputEvent = new RegisterRecipeFactoriesEvent<>(IOutput.class);
            MinecraftForge.EVENT_BUS.post(outputEvent);
            outputFactories.putAll(outputEvent.getFactories());

            RegisterRecipeFactoriesEvent<ICondition> conditionEvent = new RegisterRecipeFactoriesEvent<>(ICondition.class);
            MinecraftForge.EVENT_BUS.post(conditionEvent);
            conditionFactories.putAll(conditionEvent.getFactories());
            loadedFactories = true;
        }
    }

    @Override
    public abstract List<Recipe> loadRecipes();

    protected Recipe loadRecipe(Path root, Path file) {
        JsonContext context = null;
        String modId = context.getModId();
        String relative = root.relativize(file).toString();
        if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_")) {
            return null;
        }

        String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
        ResourceLocation key = new ResourceLocation(modId, name);

        try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
            JsonObject jsonObject = JSONUtils.fromJson(GSON, bufferedReader, JsonObject.class);
            if (jsonObject != null) {
                if (jsonObject.has("type")) {
                    String typeName = jsonObject.get("type").getAsString();
                    RecipeType recipeType = Recipes.getRecipeType(typeName);
                    if (recipeType != null) {
                        boolean loadRecipe = true;
                        if (jsonObject.has("load_conditions")) {
                            loadRecipe = CraftingHelper.processConditions(JSONUtils.getJsonArray(jsonObject, "load_conditions"));
                        }
                        if (loadRecipe) {
                            int priority = JSONUtils.getInt(jsonObject, "priority", 0);
                            JsonArray inputsJson = JSONUtils.getJsonArray(jsonObject, "inputs");
                            JsonArray outputsJson = JSONUtils.getJsonArray(jsonObject, "outputs");
                            JsonArray conditionsJson = JSONUtils.getJsonArray(jsonObject, "conditions", new JsonArray());

                            List<IInput> inputList = processJsonArray(inputsJson, inputFactories::get, "Input");
                            List<IOutput> outputList = processOutputs(outputsJson);
                            List<ICondition> conditionList = processJsonArray(conditionsJson, conditionFactories::get, "Conditions");

                            return new Recipe(key, priority, this.getRecipeSource(), recipeType, inputList, outputList, conditionList);
                        }
                    } else {
                        throw new JsonParseException("No Recipe Type found for: " + typeName);
                    }
                }
            }
        } catch (IOException | JsonParseException e) {
            RecipeSystem.LOGGER.info("Error in recipe: " + key.toString(), e);
        }
        return null;
    }

    public static List<IOutput> processOutputs(JsonArray jsonElements) {
        return processJsonArray(jsonElements, outputFactories::get, "Output");
    }

    private static <T> List<T> processJsonArray(JsonArray jsonElements, Function<String, IObjectFactory<? extends T>> factoryFunction, String name) {
        List<T> processedList = Lists.newArrayList();
        for (JsonElement element : jsonElements) {
            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();
                String typeName = JSONUtils.getString(jsonObject, "type");
                IObjectFactory<? extends T> factory = factoryFunction.apply(typeName);
                jsonObject.remove("type");
                if (factory != null) {
                    processedList.add(factory.parse(jsonObject));
                } else {
                    throw new JsonParseException("No " + name + " found for type: " + typeName);
                }
            } else {
                throw new JsonParseException(name + "s must be JSON Objects");
            }
        }
        return processedList;
    }
}

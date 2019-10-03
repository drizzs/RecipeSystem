package com.teamacronymcoders.recipesystem.recipe.output.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.teamacronymcoders.recipesystem.json.factory.IObjectFactory;
import com.teamacronymcoders.recipesystem.recipe.loader.JsonRecipeLoader;
import com.teamacronymcoders.recipesystem.recipe.output.OneOfOutput;
import net.minecraft.util.JSONUtils;

public class OneOfOutputFactory implements IObjectFactory<OneOfOutput> {
    @Override
    public OneOfOutput parse(JsonElement jsonElement) {
        JsonArray options = JSONUtils.getJsonArray(jsonElement.getAsJsonObject(), "options");
        return new OneOfOutput(JsonRecipeLoader.processOutputs(options));
    }
}

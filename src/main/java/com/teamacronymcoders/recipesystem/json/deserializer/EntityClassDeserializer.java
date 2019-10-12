package com.teamacronymcoders.recipesystem.json.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class EntityClassDeserializer implements JsonDeserializer<Class<? extends Entity>> {
    @Override
    public Class<? extends Entity> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json != null) {
            return EntityType.(new ResourceLocation(json.getAsString()));
        }

        throw new JsonParseException("Found null value for Entity");
    }
}

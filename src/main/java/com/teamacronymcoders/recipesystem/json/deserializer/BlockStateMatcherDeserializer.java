package com.teamacronymcoders.recipesystem.json.deserializer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import com.teamacronymcoders.recipesystem.block.BlockStateMatcher;
import net.minecraft.block.Block;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class BlockStateMatcherDeserializer implements JsonDeserializer<BlockStateMatcher> {
    @Override
    public BlockStateMatcher deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json != null && json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            String blockName = JSONUtils.getString(jsonObject, "block");
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName));
            if (block != null) {
                if (jsonObject.has("properties")) {
                    StateContainer stateContainer = block.getStateContainer();
                    JsonObject properties = jsonObject.getAsJsonObject("properties");
                    Map<IProperty<?>, List<?>> propertyMatching = Maps.newHashMap();
                    for (Map.Entry<String, JsonElement> entry : properties.entrySet()) {
                        IProperty<?> property = stateContainer.getProperty(entry.getKey());
                        if (property != null) {
                            List acceptableValues = Lists.newArrayList();
                            List<String> acceptableStringValues = Lists.newArrayList();
                            JsonElement jsonValues = entry.getValue();
                            if (jsonValues.isJsonArray()) {
                                jsonValues.getAsJsonArray().iterator()
                                        .forEachRemaining(value -> acceptableStringValues.add(value.getAsString()));
                            } else {
                                acceptableStringValues.add(jsonValues.getAsString());
                            }
                            for (String value: acceptableStringValues) {
                                //noinspection unchecked
                                property.parseValue(value).toJavaUtil().ifPresent(acceptableValues::add);
                            }
                            propertyMatching.put(property, acceptableValues);
                        } else {
                            throw new JsonParseException("Failed to find property: " + entry.getKey());
                        }
                    }
                    return new BlockStateMatcher(block, propertyMatching);
                } else {
                    return new BlockStateMatcher(block);
                }
            }
        }

        if (json == null) {
            throw new JsonParseException("Found null value for BlockState Input");
        } else {
            throw new JsonParseException("Failed to deserialize BlockState for: " + json.toString());
        }
    }
}

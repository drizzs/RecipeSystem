package com.teamacronymcoders.recipesystem.json.factory;

import com.google.gson.JsonElement;

public interface IObjectFactory<T> {
    T parse(JsonElement jsonElement);
}

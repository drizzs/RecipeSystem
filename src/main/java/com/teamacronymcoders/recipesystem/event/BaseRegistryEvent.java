package com.teamacronymcoders.recipesystem.event;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.GenericEvent;

import java.util.Map;

public class BaseRegistryEvent<T> extends GenericEvent<T> {
    private Map<String, T> entries;

    public BaseRegistryEvent(Class<T> type) {
        super(type);
        entries = Maps.newHashMap();
    }

    public void register(ResourceLocation resourceLocation, T entry) {
        entries.put(resourceLocation.toString(), entry);
    }

    public Map<String, T> getEntries() {
        return ImmutableMap.copyOf(entries);
    }
}

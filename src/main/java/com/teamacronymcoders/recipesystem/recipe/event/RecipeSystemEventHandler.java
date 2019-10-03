package com.teamacronymcoders.recipesystem.recipe.event;

import com.teamacronymcoders.recipesystem.event.BaseRegistryEvent;
import com.teamacronymcoders.recipesystem.recipe.Recipes;
import com.teamacronymcoders.recipesystem.recipe.condition.BiomeCondition;
import com.teamacronymcoders.recipesystem.recipe.condition.ICondition;
import com.teamacronymcoders.recipesystem.recipe.condition.VillageCondition;
import com.teamacronymcoders.recipesystem.recipe.input.BlockStateInput;
import com.teamacronymcoders.recipesystem.recipe.input.EntityInput;
import com.teamacronymcoders.recipesystem.recipe.input.ForgeEnergyInput;
import com.teamacronymcoders.recipesystem.recipe.input.IInput;
import com.teamacronymcoders.recipesystem.recipe.loader.AssetJsonRecipeLoader;
import com.teamacronymcoders.recipesystem.recipe.loader.ILoader;
import com.teamacronymcoders.recipesystem.recipe.output.*;
import com.teamacronymcoders.recipesystem.recipe.output.json.OneOfOutputFactory;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import static com.teamacronymcoders.recipesystem.RecipeSystem.MOD_ID;


@EventBusSubscriber(modid = MOD_ID)
public class RecipeSystemEventHandler {
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent<IRecipeSerializer<?>> recipeRegistryEvent) {
        Recipes.loadRecipeTypes();
        Recipes.loadRecipes(false);
    }

    @SubscribeEvent
    public static void registerConditions(RegisterRecipeFactoriesEvent<ICondition> conditionEvent) {
        conditionEvent.register(new ResourceLocation(MOD_ID, "biome"), BiomeCondition.class);
        conditionEvent.register(new ResourceLocation(MOD_ID, "village"), VillageCondition.class);
    }

    @SubscribeEvent
    public static void registerInput(RegisterRecipeFactoriesEvent<IInput> inputEvent) {
        inputEvent.register(new ResourceLocation(MOD_ID, "blockstate"), BlockStateInput.class);
        inputEvent.register(new ResourceLocation(MOD_ID, "forge_energy"), ForgeEnergyInput.class);
        inputEvent.register(new ResourceLocation(MOD_ID, "entity"), EntityInput.class);
    }

    @SubscribeEvent
    public static void registerOutput(RegisterRecipeFactoriesEvent<IOutput> outputEvent) {
        outputEvent.register(new ResourceLocation(MOD_ID, "command"), CommandOutput.class);
        outputEvent.register(new ResourceLocation(MOD_ID, "blockstate"), BlockStateOutput.class);
        outputEvent.register(new ResourceLocation(MOD_ID, "one_of"), new OneOfOutputFactory());
        outputEvent.register(new ResourceLocation(MOD_ID, "entity"), EntityOutput.class);
        outputEvent.register(new ResourceLocation(MOD_ID, "explosion"), ExplosionOutput.class);
    }

    @SubscribeEvent
    public static void registerLoader(BaseRegistryEvent<ILoader> loaderRegistryEvent) {
        loaderRegistryEvent.register(new ResourceLocation(MOD_ID, "asset"), AssetJsonRecipeLoader.getInstance());
        Base.proxy.registerServerLoader(loaderRegistryEvent);
    }
}

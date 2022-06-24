package net.fayorg.betterresources.event;

import net.fayorg.betterresources.BetterResourcesMod;
import net.fayorg.betterresources.recipe.EnrichingCatalystRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterResourcesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, EnrichingCatalystRecipe.Type.ID, EnrichingCatalystRecipe.Type.INSTANCE);
    }

}

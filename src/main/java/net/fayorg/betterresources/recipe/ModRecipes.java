package net.fayorg.betterresources.recipe;

import net.fayorg.betterresources.BetterResourcesMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BetterResourcesMod.MODID);

    public static final RegistryObject<RecipeSerializer<EnrichingCatalystRecipe>> ENRICHING_CATALYST_SERIALIZER = SERIALIZERS.register("enriching_catalyst", () -> EnrichingCatalystRecipe.Serializer.INSTANCE);

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }

}

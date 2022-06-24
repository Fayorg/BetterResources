package net.fayorg.betterresources.item;

import net.fayorg.betterresources.BetterResourcesMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterResourcesMod.MODID);

    public static final RegistryObject<Item> SPLITIUM_RAW = ITEMS.register("splitium_raw",
            () -> new Item(new Item.Properties().tab(BetterResourcesMod.BETTERTAB)));

    public static final RegistryObject<Item> SPLITIUM_GEM = ITEMS.register("splitium_gem",
            () -> new Item(new Item.Properties().tab(BetterResourcesMod.BETTERTAB)));

    public static final RegistryObject<Item> SPLITIUM_ENRICHED = ITEMS.register("splitium_enriched",
            () -> new Item(new Item.Properties().tab(BetterResourcesMod.BETTERTAB)));

    public static final RegistryObject<Item> SPLITIUM_UNSTABLE = ITEMS.register("splitium_unstable",
            () -> new UnstableGemItem(new Item.Properties().tab(BetterResourcesMod.BETTERTAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}

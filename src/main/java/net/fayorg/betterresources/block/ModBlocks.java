package net.fayorg.betterresources.block;

import net.fayorg.betterresources.BetterResourcesMod;
import net.fayorg.betterresources.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterResourcesMod.MODID);

    public static final RegistryObject<Block> SPLITIUM_ORE = registerBlock("splitium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SPLITIUM_DEEPSLATE = registerBlock("splitium_deepslate",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(7f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SPLITIUM_BLOCK = registerBlock("splitium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(5f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SPLITIUM_ENRICHED_BLOCK = registerBlock("splitium_enriched_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ENRICHING_CATALYST = registerBlock("enriching_catalyst",
            () -> new EnrichingCatalystBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(5f)), new Item.Properties().tab(BetterResourcesMod.BETTERTAB).stacksTo(1));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, BetterResourcesMod.BETTERTAB);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Item.Properties itemProperties) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, itemProperties);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, Item.Properties itemProp) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), itemProp));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

}

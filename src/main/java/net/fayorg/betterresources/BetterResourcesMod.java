package net.fayorg.betterresources;

import com.mojang.logging.LogUtils;
import net.fayorg.betterresources.block.ModBlocks;
import net.fayorg.betterresources.block.entity.ModBlockEntity;
import net.fayorg.betterresources.entity.ModEntities;
import net.fayorg.betterresources.entity.renderer.ThrownUnstableGemRenderer;
import net.fayorg.betterresources.item.ModItems;
import net.fayorg.betterresources.recipe.ModRecipes;
import net.fayorg.betterresources.screen.EnrichingCatalystScreen;
import net.fayorg.betterresources.screen.ModMenuTypes;
import net.fayorg.betterresources.tab.BetterResourceTab;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

@Mod(BetterResourcesMod.MODID)
public class BetterResourcesMod
{
    public static final String MODID = "betterresources";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static CreativeModeTab BETTERTAB = new BetterResourceTab(BetterResourcesMod.MODID);

    public BetterResourcesMod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntity.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);
        ModEntities.register(eventBus);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    /*
        TODO: Add the mining level & tool
        TODO: Added Enriching Catalyst Texture
        TODO: Create Texture for Unstable Gem (animated?)
        TODO: Create the resource finder machine block
        TODO: Integrate Recipes in JEI
     */

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void clientSetup(final FMLClientSetupEvent event) {

        MenuScreens.register(ModMenuTypes.ENRICHING_CATALYST_MENU.get(), EnrichingCatalystScreen::new);
        EntityRenderers.register(ModEntities.UNSTABLE_GEM.get(), ThrownUnstableGemRenderer::new);

    }
}

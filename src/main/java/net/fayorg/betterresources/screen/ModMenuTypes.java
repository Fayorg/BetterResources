package net.fayorg.betterresources.screen;

import net.fayorg.betterresources.BetterResourcesMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BetterResourcesMod.MODID);

    public static final RegistryObject<MenuType<EnrichingCatalystMenu>> ENRICHING_CATALYST_MENU = registerMenu(EnrichingCatalystMenu::new, "enriching_catalyst_menu");

    public static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenu(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }

}

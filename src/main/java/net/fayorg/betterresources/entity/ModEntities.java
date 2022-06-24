package net.fayorg.betterresources.entity;

import net.fayorg.betterresources.BetterResourcesMod;
import net.fayorg.betterresources.entity.throwable.UnstableGem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, BetterResourcesMod.MODID);

    public static final RegistryObject<EntityType<UnstableGem>> UNSTABLE_GEM = ENTITIES.register("unstable_gem_entity",
            () -> EntityType.Builder.of((EntityType.EntityFactory<UnstableGem>)UnstableGem::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(new ResourceLocation(BetterResourcesMod.MODID, "unstable_gem").toString()));

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }

}

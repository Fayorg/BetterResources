package net.fayorg.betterresources.block.entity;

import net.fayorg.betterresources.BetterResourcesMod;
import net.fayorg.betterresources.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntity {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, BetterResourcesMod.MODID);

    public static final RegistryObject<BlockEntityType<EnrichingCatalystBlockEntity>> ENRICHING_CATALYST = BLOCK_ENTITY.register("enriching_catalyst_block_entity",
            () -> BlockEntityType.Builder.of(EnrichingCatalystBlockEntity::new, ModBlocks.ENRICHING_CATALYST.get()).build(null));

    public static final RegistryObject<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR = BLOCK_ENTITY.register("extractor_block_entity",
            () -> BlockEntityType.Builder.of(ExtractorBlockEntity::new, ModBlocks.EXTRACTOR.get()).build(null));
    public static void register(IEventBus bus) {
        BLOCK_ENTITY.register(bus);
    }

}

package net.fayorg.betterresources.tab;

import net.fayorg.betterresources.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BetterResourceTab extends CreativeModeTab {

    public BetterResourceTab(String label) {
        super(label);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.SPLITIUM_GEM.get());
    }
}

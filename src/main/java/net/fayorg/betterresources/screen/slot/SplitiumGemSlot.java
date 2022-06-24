package net.fayorg.betterresources.screen.slot;

import net.fayorg.betterresources.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SplitiumGemSlot extends SlotItemHandler {

    public SplitiumGemSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem().equals(ModItems.SPLITIUM_GEM.get());
    }
}

package net.fayorg.betterresources.block.entity;

import net.fayorg.betterresources.recipe.ExtractorRecipe;
import net.fayorg.betterresources.utils.IngredientWithRarity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class ExtractorBlockEntity extends BlockEntity implements MenuProvider {

    private LazyOptional<IItemHandler> lazyOptional = LazyOptional.empty();

    //private final IngredientWithRarity[] possibleOutputs;

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    public ExtractorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntity.EXTRACTOR.get(), pWorldPosition, pBlockState);
        //this.level.getRecipeManager().
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.betterresources.extractor.name");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyOptional.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyOptional = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyOptional.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemStackHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ExtractorBlockEntity pBlockEntity) {
        // TODO: Updating the progress (and animations)
        /*if(pBlockEntity.hasContainerUp(pLevel, pPos)) {
            int i = 0;
            for(ExtractorRecipe recipe : pLevel.getRecipeManager().getAllRecipesFor(ExtractorRecipe.Type.INSTANCE)) {
                int finalI = i;
                recipe.getOutputs().forEach(((ingredient, aFloat) -> {
                    System.out.println("Entry for index " + finalI + ": " + ingredient.toJson() + " Rarity " + aFloat);
                }));
                i++;
            }
        }*/
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(pLevel.getRecipeManager().getAllRecipesFor(ExtractorRecipe.Type.INSTANCE).toString());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private boolean hasContainerUp(Level level, BlockPos pos) {
        if(level.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())) instanceof RandomizableContainerBlockEntity) {
            return true;
        }
        return false;
    }

}

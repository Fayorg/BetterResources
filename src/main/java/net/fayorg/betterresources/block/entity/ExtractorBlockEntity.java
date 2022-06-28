package net.fayorg.betterresources.block.entity;

import net.fayorg.betterresources.utils.EnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;

public class ExtractorBlockEntity extends BlockEntity {

    private LazyOptional<IItemHandler> lazyOptional = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> energyStorageLazyOptional = LazyOptional.empty();

    //private final IngredientWithRarity[] possibleOutputs;
    
    // This is the process time in ticks (not shown, just for timing)
    private final int maxProcess = 200;
    private int process = 0;

    private final int energyUseByTick = 100;

    private final ArrayList<Item> acceptedInputs = new ArrayList<Item>(Arrays.asList(Items.COBBLESTONE, Items.SAND));

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final EnergyStorage energyStorage = new EnergyStorage(20000, 200) {
        @Override
        public void onChange() {
            setChanged();
            // TODO Send Energy level to client with custom network packet
        }
    };

    public ExtractorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntity.EXTRACTOR.get(), pWorldPosition, pBlockState);
        //this.level.getRecipeManager().
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyOptional.cast();
        }
        if(cap == CapabilityEnergy.ENERGY) {
            return energyStorageLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyOptional = LazyOptional.of(() -> itemStackHandler);
        energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyOptional.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemStackHandler.serializeNBT());
        tag.putInt("energy", energyStorage.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemStackHandler.deserializeNBT(nbt.getCompound("inventory"));
        energyStorage.setEnergy(nbt.getInt("energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ExtractorBlockEntity pBlockEntity) {
        if(pBlockEntity.energyStorage.canExtract(pBlockEntity.energyUseByTick)) {
            if(!pBlockEntity.acceptedInputs.contains(pBlockEntity.itemStackHandler.getStackInSlot(0).getItem())) {
                pBlockEntity.process++;
                pBlockEntity.energyStorage.extractEnergy(pBlockEntity.energyUseByTick, false);
                if(pBlockEntity.process >= pBlockEntity.maxProcess) {
                    pBlockEntity.process = 0;
                    ItemStack stack = new ItemStack(Items.DIAMOND_BLOCK, 1);
                    pBlockEntity.addOutputItem(pBlockEntity, pLevel, pPos, stack);
                }
            }
        }
        if(pBlockEntity.hasContainerUp(pLevel, pPos) && !pBlockEntity.itemStackHandler.getStackInSlot(1).equals(ItemStack.EMPTY)) {
            pBlockEntity.addOutputItem(pBlockEntity, pLevel, pPos, pBlockEntity.itemStackHandler.getStackInSlot(1));
        }
    }

    private boolean hasContainerUp(Level level, BlockPos pos) {
        return level.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())) instanceof RandomizableContainerBlockEntity;
    }

    private void addOutputItem(ExtractorBlockEntity bentity, Level level, BlockPos pos, ItemStack stack) {
        if(level.isClientSide) return;
        if(level.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())) instanceof RandomizableContainerBlockEntity container) {
            for(int i = 0; i < container.getContainerSize(); i++) {
                if(container.getItem(i).equals(ItemStack.EMPTY)) {
                    container.setItem(i, stack);
                    return;
                }
                if(container.getItem(i).getItem().equals(stack.getItem())) {
                    stack.setCount(container.getItem(i).getCount() + stack.getCount());
                    container.setItem(i, stack);
                    return;
                }
            }
            if(bentity.itemStackHandler.getStackInSlot(1).getItem().equals(stack.getItem())) {
                stack.setCount(bentity.itemStackHandler.getStackInSlot(1).getCount() + stack.getCount());
                bentity.itemStackHandler.setStackInSlot(1, stack);
            }
        } else {
            System.out.println("No container above!");
            System.out.println("Current content: " + bentity.itemStackHandler.getStackInSlot(1).getItem().toString() + "x " + bentity.itemStackHandler.getStackInSlot(1).getItem().toString());
            if(bentity.itemStackHandler.getStackInSlot(1).getItem().equals(stack.getItem())) {
                System.out.println("Same Item!");
                stack.setCount(bentity.itemStackHandler.getStackInSlot(1).getCount() + stack.getCount());
                bentity.itemStackHandler.setStackInSlot(1, stack);
                return;
            }
            if(bentity.itemStackHandler.getStackInSlot(1).getItem().equals(ItemStack.EMPTY.getItem())) {
                System.out.println("No Item!");
                bentity.itemStackHandler.setStackInSlot(1, stack);
                return;
            }
        }
    }

    public ItemStack getStoredOutput(ExtractorBlockEntity entity) {
        return entity.itemStackHandler.getStackInSlot(1);
    }

}

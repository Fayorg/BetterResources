package net.fayorg.betterresources.item;

import net.fayorg.betterresources.entity.throwable.UnstableGem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UnstableGemItem extends Item {
    public UnstableGemItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if(!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        if (!pLevel.isClientSide) {
            UnstableGem unstableGem = new UnstableGem(pLevel, pPlayer);
            unstableGem.setItem(itemstack);
            unstableGem.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 3.0F, 1.0F);
            pLevel.addFreshEntity(unstableGem);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(pEntity instanceof Player player) {
            if(!player.getAbilities().instabuild) {
                prepareExplode(pStack, pLevel, pEntity, pSlotId);
            } else {
                return;
            }
        } else {
            prepareExplode(pStack, pLevel, pEntity, pSlotId);
        }
    }

    private void prepareExplode(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId) {
        if(pStack.hasTag()) {
            int ticksBeforeExplode = pStack.getTag().getInt("betterresources.ticksBeforeExplode");
            ticksBeforeExplode--;
            if(ticksBeforeExplode == 0) {
                if(!pLevel.isClientSide) {
                    pLevel.explode(null, pEntity.getX(), pEntity.getY(), pEntity.getZ(), 5.0f, true, Explosion.BlockInteraction.BREAK);
                    pEntity.getSlot(pSlotId).set(ItemStack.EMPTY);
                }
            } else {
                pStack.getTag().putInt("betterresources.ticksBeforeExplode", ticksBeforeExplode);
            }
        } else {
            pStack.setTag(new CompoundTag());
            pStack.getTag().putInt("betterresources.ticksBeforeExplode", 200);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(pStack.hasTag()) {
            int time = pStack.getTag().getInt("betterresources.ticksBeforeExplode") / 20;
            pTooltipComponents.add(new TextComponent("Explosion in " + time + " seconds"));
        }
    }
}

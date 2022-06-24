package net.fayorg.betterresources.item.throwable;

import net.fayorg.betterresources.entity.ModEntities;
import net.fayorg.betterresources.item.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class UnstableGem extends ThrowableItemProjectile {
    public UnstableGem(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.UNSTABLE_GEM.get(), pShooter, pLevel);
    }

    public UnstableGem(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.UNSTABLE_GEM.get(), pX, pY, pZ, pLevel);
    }

    public UnstableGem(EntityType<? extends ThrowableItemProjectile> entityEntityType, Level level) {
        super(entityEntityType, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SPLITIUM_UNSTABLE.get();
    }
}

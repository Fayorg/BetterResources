package net.fayorg.betterresources.entity.throwable;

import net.fayorg.betterresources.entity.ModEntities;
import net.fayorg.betterresources.item.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class UnstableGem extends ThrowableItemProjectile {
    public UnstableGem(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.UNSTABLE_GEM.get(), pShooter, pLevel);
    }

    public UnstableGem(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.UNSTABLE_GEM.get(), pX, pY, pZ, pLevel);
    }

    public UnstableGem(EntityType<? extends UnstableGem> entityEntityType, Level level) {
        super(entityEntityType, level);
    }

    @Override
    public Item getDefaultItem() {
        return ModItems.SPLITIUM_UNSTABLE.get().asItem();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (ParticleOptions)(itemstack.isEmpty() ? ModEntities.UNSTABLE_GEM : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particleoptions = this.getParticle();
            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void onHit(HitResult pResult) {
        this.level.explode(this, pResult.getLocation().x, pResult.getLocation().y, pResult.getLocation().z, 5.0f, true, Explosion.BlockInteraction.BREAK);
        this.discard();
    }
}

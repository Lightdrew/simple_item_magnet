package me.lightdrew.simpleItemMagnet.mixin;

import me.lightdrew.simpleItemMagnet.ModConfig;
import me.lightdrew.simpleItemMagnet.data.ModData;
import me.lightdrew.simpleItemMagnet.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin extends Avatar {
    @Shadow
    @Final
    Inventory inventory;


    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void simple_item_magnet$tick(CallbackInfo ci)
    {
        final Item magnet = ModItems.ITEM_MAGNET.get();

        if(isCrouching() || isDescending() || isSpectator()) return;

        boolean hasActiveMagnet = false;

        for(ItemStack item: inventory)
        {
            if(!item.is(magnet)) continue;
            if(!item.getOrDefault(ModData.MAGNET_ACTIVE.get(), false)) continue;

            hasActiveMagnet = true;
            break;
        }

        if (!hasActiveMagnet) return;

        final Vec3 playerPos = position();

        final double range = ModConfig.magnet_pull_distance;
        final boolean isModeEuclidean = ModConfig.magnet_distance_mode.equals(ModConfig.DistanceMode.EUCLIDEAN);

        for (Entity entity : level().getEntities(this, new AABB(playerPos.add(-range), playerPos.add(range))))
        {
            //Ignore non-items
            if (!(entity instanceof ItemEntity item)) continue;

            //Get distance between player and item
            final Vec3 ray = item.position().subtract(playerPos);
            final double distSqr = ray.dot(ray);

            //Reject items out of spherical range if magnet is in Spherical mode
            if(isModeEuclidean && range*range - distSqr < 0) continue;

            final double force = 0.2;
            final Vec3 normal = ray.normalize();


            if(distSqr < 2.5e-1)
            {
                item.setPos(playerPos);
                item.setDeltaMovement(item.getDeltaMovement().scale(0.01));
                item.setNoPickUpDelay();
                continue;
            }

            double speedAttenuation = 8.0*item.getDeltaMovement().lengthSqr();
            if (speedAttenuation < 1.0) speedAttenuation = 1.0;

            item.addDeltaMovement(normal.scale(-force/speedAttenuation));

            double x = item.getX(), y = item.getY(), z = item.getZ();
            if (level().getRandom().nextInt(3) == 0) level().addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z,0,0,0);

        }
    }

}

package me.lightdrew.simpleItemMagnet.item;

import me.lightdrew.simpleItemMagnet.data.ModData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MagnetItem extends Item {
    public MagnetItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand) {

        if (interactionHand != InteractionHand.MAIN_HAND || !(player.isCrouching() || player.isDescending()))
            return InteractionResult.PASS;


        final DataComponentType<Boolean> magnet_active = ModData.MAGNET_ACTIVE.getOrNull();
        final ItemStack heldItem = player.getMainHandItem();
        final boolean active = !heldItem.getComponents().getOrDefault(magnet_active, false);

        final String translationKey = active ? "item.simple_item_magnet.item_magnet.active" : "item.simple_item_magnet.item_magnet";
        final float pitch = (active ? 1.1f : 0.7f);

        heldItem.set(DataComponents.ITEM_NAME, Component.translatable(translationKey));

        heldItem.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, active);
        heldItem.set(magnet_active, active);

        level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0f, pitch);

        return InteractionResult.SUCCESS;
    }
}
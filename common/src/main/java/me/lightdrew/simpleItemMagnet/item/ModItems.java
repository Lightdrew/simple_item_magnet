package me.lightdrew.simpleItemMagnet.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;
import java.util.function.Supplier;

import static me.lightdrew.simpleItemMagnet.Main.MOD_ID;
import static me.lightdrew.simpleItemMagnet.Main.MOD_LOG;

public class ModItems
{

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> ITEM_MAGNET = register("item_magnet", MagnetItem::new, () -> new Item.Properties().stacksTo(1).arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES));

    public static RegistrySupplier<Item> register(String name, Function<Item.Properties, Item> itemFactory, Supplier<Item.Properties> propertyFactory) {
        // Create the item key.
        return ITEMS.register(name, () -> itemFactory.apply(propertyFactory.get()
                .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name)))
        ));
    }

    public static void init() {
        ITEMS.register();
        MOD_LOG.info("[{}] Items loaded.", MOD_ID);
    }
}

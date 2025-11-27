package me.lightdrew.simpleItemMagnet.neoforge;

import me.lightdrew.simpleItemMagnet.Main;
import me.lightdrew.simpleItemMagnet.ModConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import static me.lightdrew.simpleItemMagnet.Main.MOD_ID;

@Mod(MOD_ID)
public final class MainNeoForge {
    public MainNeoForge() {
        // Run our common setup.
        Main.init();

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> ModConfig.getConfigScreen(parent)
        );
    }
}

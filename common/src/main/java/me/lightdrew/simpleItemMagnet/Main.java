package me.lightdrew.simpleItemMagnet;

import me.lightdrew.simpleItemMagnet.data.ModData;
import me.lightdrew.simpleItemMagnet.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    public static final String MOD_ID = "simple_item_magnet";
    public static final Logger MOD_LOG = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        MOD_LOG.info("[{}] Mod initialized.",MOD_ID);
        ModData.init();
        ModItems.init();
        MOD_LOG.info("Mod loaded.");
    }
}

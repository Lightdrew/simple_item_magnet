package me.lightdrew.simpleItemMagnet;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModConfig
{
    @SerialEntry(comment = "Magnet Pull Range\n" +
            "Accepted values are in range [0,24]")
    public static int magnet_pull_distance = 8;

    public enum DistanceMode implements NameableEnum {
        CARDINAL,
        EUCLIDEAN;

        @Override
        public Component getDisplayName() {
            return Component.translatable("text.config.simple-item-magnet.distancemode." + name().toLowerCase());
        }
    }

    @SerialEntry(comment = "Allowed Values: CARDINAL, SPHERICAL\n" +
            "CARDINAL: All items which have all XYZ axis components within range from the player will be pulled\n" +
            "Only items within a sphere with the magnet's range as radius centered on the player will be pulled.")
    public static DistanceMode magnet_distance_mode = DistanceMode.EUCLIDEAN;


    public static Screen getConfigScreen(Screen parent) {
        final var config = YetAnotherConfigLib.createBuilder()
                .title(Component.literal("text.config.simple-item-magnet.title"))
                .category(ConfigCategory.createBuilder()
                    .name(Component.translatable("text.config.simple-item-magnet.title"))
                    .option(Option.<Integer>createBuilder()
                            .name(Component.translatable("text.config.simple-item-magnet.option.magnet_pull_distance"))
                            .description(OptionDescription.of(Component.translatable("text.config.simple-item-magnet.option.magnet_pull_distance.tooltip")
                            ))
                            .binding(8, () -> magnet_pull_distance, newVal -> magnet_pull_distance = newVal)
                            .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                    .range(2,24)
                                    .step(1)
                            )
                            .build())
                    .option(Option.<DistanceMode>createBuilder()
                        .name(Component.translatable("text.config.simple-item-magnet.option.magnet_distance_mode"))
                        .description(OptionDescription.of(
                                Component.translatable("text.config.simple-item-magnet.option.magnet_distance_mode.tooltip")
                        ))
                        .binding(DistanceMode.EUCLIDEAN, () -> magnet_distance_mode, newVal -> magnet_distance_mode = newVal)
                        .controller(opt -> EnumControllerBuilder.create(opt)
                                .enumClass(DistanceMode.class))
                    .build())
                .build())
        .build();

        return config.generateScreen(parent);
    }
}

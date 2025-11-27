package me.lightdrew.simpleItemMagnet.data;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.function.UnaryOperator;

import static me.lightdrew.simpleItemMagnet.Main.MOD_ID;
import static me.lightdrew.simpleItemMagnet.Main.MOD_LOG;

public class ModData {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(MOD_ID, Registries.DATA_COMPONENT_TYPE);
    public static final RegistrySupplier<DataComponentType<Boolean>> MAGNET_ACTIVE = register("magnet_active", (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    private static <T> RegistrySupplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {

        return DATA_COMPONENT_TYPES.register(name,() -> unaryOperator.apply(DataComponentType.builder()).build());
    }

    public static void init()
    {
        DATA_COMPONENT_TYPES.register();
        MOD_LOG.info("[{}] Data components loaded.", MOD_ID);
    }
}

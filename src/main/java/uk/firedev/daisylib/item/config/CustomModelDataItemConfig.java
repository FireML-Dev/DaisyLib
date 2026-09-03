package uk.firedev.daisylib.item.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.yaml.snakeyaml.tokens.StreamEndToken;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.utils.CommonUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class CustomModelDataItemConfig extends ItemConfig<Number> {

    private static final Method MODERN_GETTER_METHOD;
    private static final Method SET_FLOATS;
    private static final Method MODERN_SETTER_METHOD;

    static {
        Class<?> clazz = CommonUtils.getClassOrNull("org.bukkit.inventory.meta.components.CustomModelDataComponent");
        Method getter = null;
        Method setFloats = null;
        Method setter = null;
        if (clazz != null) {
            getter = CommonUtils.getMethodOrNull(ItemMeta.class, "getCustomModelDataComponent");
            setFloats = CommonUtils.getMethodOrNull(clazz, "setFloats", List.class);
            setter = CommonUtils.getMethodOrNull(ItemMeta.class, "setCustomModelDataComponent", clazz);
        }
        MODERN_GETTER_METHOD = getter;
        SET_FLOATS = setFloats;
        MODERN_SETTER_METHOD = setter;
    }

    public CustomModelDataItemConfig(@NonNull ConfigurationSection section) {
        super(section);
    }

    public CustomModelDataItemConfig(@NonNull CustomModelDataItemConfig base) {
        super(base);
    }

    @Override
    @Nullable
    public Integer getConfiguredValue() {
        String str = section.getString("custom-model-data");
        if (str == null) {
            return null;
        }
        return CommonUtils.getInt(str);
    }

    @Override
    protected BiConsumer<ItemStack, Number> applyToItem(@Nullable OfflinePlayer player, @Nullable Map<String, ?> replacements) {
        return (item, value) -> item.editMeta(meta -> {
            // 1.21.4+ :)
            if (MODERN_GETTER_METHOD != null) {
                applyModern(meta, value);
            // Not 1.21.4+ :(
            } else {
                applyLegacy(meta, value.intValue());
            }
        });
    }

    private void applyModern(@NonNull ItemMeta meta, @NonNull Number value) {
        try {
            Object component = MODERN_GETTER_METHOD.invoke(meta);
            SET_FLOATS.invoke(component, List.of(value.floatValue()));
            MODERN_SETTER_METHOD.invoke(meta, component);
        // If any error is thrown, fall back to legacy and warn.
        } catch (InvocationTargetException | IllegalAccessException e) {
            DaisyLib.get().getLogging().warn("Failed to apply modern CustomModelData.", e);
            applyLegacy(meta, value.intValue());
        }
    }

    private void applyLegacy(@NonNull ItemMeta meta, int value) {
        meta.setCustomModelData(value);
    }

    @Override
    public @NonNull CustomModelDataItemConfig createCopy() {
        return new CustomModelDataItemConfig(this);
    }

}

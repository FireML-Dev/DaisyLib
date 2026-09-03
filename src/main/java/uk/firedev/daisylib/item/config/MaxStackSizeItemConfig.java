package uk.firedev.daisylib.item.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;

public class MaxStackSizeItemConfig extends ItemConfig<Integer> {

    public MaxStackSizeItemConfig(@NonNull ConfigurationSection section) {
        super(section);
    }

    public MaxStackSizeItemConfig(@NonNull MaxStackSizeItemConfig base) {
        super(base);
    }

    @Override
    public @Nullable Integer getConfiguredValue() {
        return section.getInt("max-stack-size", null);
    }

    @Override
    protected BiConsumer<ItemStack, Integer> applyToItem(@Nullable OfflinePlayer player, @Nullable Map<String, ?> replacements) {
        return (item, value) -> {
            if (value == null) {
                return;
            }
            int finalValue = Math.clamp(value, 1, 99);
            item.editMeta(meta -> meta.setMaxStackSize(finalValue));
        };
    }

    @Override
    public @NonNull MaxStackSizeItemConfig createCopy() {
        return new MaxStackSizeItemConfig(this);
    }

}

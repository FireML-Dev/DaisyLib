package uk.firedev.daisylib.item.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;

public class QuantityItemConfig extends ItemConfig<Integer> {

    public QuantityItemConfig(@NonNull ConfigurationSection section) {
        super(section);
    }

    public QuantityItemConfig(@NonNull QuantityItemConfig base) {
        super(base);
    }

    @Override
    public @NonNull Integer getConfiguredValue() {
        return section.getInt("quantity", 1);
    }

    @Override
    protected BiConsumer<ItemStack, Integer> applyToItem(@Nullable OfflinePlayer player, @Nullable Map<String, ?> replacements) {
        return ItemStack::setAmount;
    }

    @Override
    public @NonNull QuantityItemConfig createCopy() {
        return new QuantityItemConfig(this);
    }

}

package uk.firedev.daisylib.item.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;

public class FireResistantItemConfig extends ItemConfig<Boolean> {

    public FireResistantItemConfig(@NonNull ConfigurationSection section) {
        super(section);
    }

    public FireResistantItemConfig(@NonNull FireResistantItemConfig base) {
        super(base);
    }

    @Override
    public Boolean getConfiguredValue() {
        return section.getBoolean("fire-resistant", false);
    }

    @Override
    protected BiConsumer<ItemStack, Boolean> applyToItem(@Nullable OfflinePlayer player, @Nullable Map<String, ?> replacements) {
        return (item, value) ->
            item.editMeta(meta -> meta.setFireResistant(value));
    }

    @Override
    public @NonNull FireResistantItemConfig createCopy() {
        return new FireResistantItemConfig(this);
    }

}

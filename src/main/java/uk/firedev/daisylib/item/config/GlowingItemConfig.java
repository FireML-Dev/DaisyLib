package uk.firedev.daisylib.item.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;

public class GlowingItemConfig extends ItemConfig<Boolean> {

    public GlowingItemConfig(@NonNull ConfigurationSection section) {
        super(section);
    }

    public GlowingItemConfig(@NonNull GlowingItemConfig base) {
        super(base);
    }

    @Override
    public @NonNull Boolean getConfiguredValue() {
        return section.getBoolean("glowing", false);
    }

    @Override
    protected BiConsumer<ItemStack, Boolean> applyToItem(@Nullable OfflinePlayer player, @Nullable Map<String, ?> replacements) {
        return (item, value) ->
            item.editMeta(meta -> meta.setEnchantmentGlintOverride(value));
    }

    @Override
    public @NonNull GlowingItemConfig createCopy() {
        return new GlowingItemConfig(this);
    }

}

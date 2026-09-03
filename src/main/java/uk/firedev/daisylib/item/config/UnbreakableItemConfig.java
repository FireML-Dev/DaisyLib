package uk.firedev.daisylib.item.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;

public class UnbreakableItemConfig extends ItemConfig<Boolean> {

    public UnbreakableItemConfig(@NonNull ConfigurationSection section) {
        super(section);
    }

    public UnbreakableItemConfig(@NonNull UnbreakableItemConfig base) {
        super(base);
    }

    @Override
    public @NonNull Boolean getConfiguredValue() {
        return section.getBoolean("unbreakable", false);
    }

    @Override
    protected BiConsumer<ItemStack, Boolean> applyToItem(@Nullable OfflinePlayer player, @Nullable Map<String, ?> replacements) {
        return (item, value) -> item.editMeta(meta -> meta.setUnbreakable(value));
    }

    @Override
    public @NonNull UnbreakableItemConfig createCopy() {
        return new UnbreakableItemConfig(this);
    }

}

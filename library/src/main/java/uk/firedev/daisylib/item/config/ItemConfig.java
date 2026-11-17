package uk.firedev.daisylib.item.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.messages.replacer.Replacer;

import java.util.function.BiConsumer;
import java.util.function.Function;

@ApiStatus.Internal
public abstract class ItemConfig<T> {

    protected T def;
    protected Function<T, T> transformer;
    protected final ConfigurationSection section;
    protected boolean enabled = true;

    public ItemConfig(@NonNull ConfigurationSection section) {
        this.section = section;
    }

    protected ItemConfig(@NonNull ItemConfig<T> base) {
        super();
        this.def = base.def;
        this.transformer = base.transformer;
        this.section = base.section;
        this.enabled = base.enabled;
    }

    public @Nullable T getActualValue() {
        T configured = getConfiguredValue();
        if (configured == null) {
            return def;
        }
        return configured;
    }

    /**
     * Applies the actual value to the item if this config is enabled.
     *
     * @param item     The item to apply the config to.
     * @param replacer
     */
    public void apply(@NonNull ItemStack item, @Nullable OfflinePlayer player, @Nullable Replacer replacer) {
        if (!enabled) {
            return;
        }
        T value = transformer == null ? getActualValue() : transformer.apply(getActualValue());
        if (value != null) {
            applyToItem(player, replacer).accept(item, value);
        }
    }

    public abstract @Nullable T getConfiguredValue();

    protected abstract BiConsumer<ItemStack, T> applyToItem(@Nullable OfflinePlayer player, @Nullable Replacer replacer);

    public void setDefault(@Nullable T def) {
        this.def = def;
    }

    public void setTransformer(@Nullable Function<T, T> transformer) {
        this.transformer = transformer;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public abstract @NonNull ItemConfig<T> createCopy();

}

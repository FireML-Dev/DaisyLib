package uk.firedev.daisylib.addons.item;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.addons.InvalidAddonException;
import uk.firedev.daisylib.registry.Registry;
import uk.firedev.daisylib.util.Loggers;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class ItemAddonRegistry implements Registry<ItemAddon> {

    private static final ItemAddonRegistry instance = new ItemAddonRegistry();

    private final Map<String, ItemAddon> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private ItemAddonRegistry() {}

    public static @NonNull ItemAddonRegistry get() {
        return instance;
    }

    @NonNull
    @Override
    public Map<String, ItemAddon> getRegistry() {
        return Map.copyOf(registry);
    }

    @Nullable
    @Override
    public ItemAddon get(@NonNull String key) {
        return registry.get(key);
    }

    @NonNull
    @Override
    public ItemAddon getOrDefault(@NonNull String key, @NonNull ItemAddon defaultValue) {
        return registry.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean unregister(@NonNull String key) {
        return registry.remove(key) != null;
    }

    @Override
    public boolean register(@NonNull ItemAddon value, boolean force) {
        if (!force && registry.containsKey(value.getKey())) {
            return false;
        }
        registry.put(value.getKey(), value);
        return true;
    }

    public @Nullable ItemStack processString(@Nullable String string) {
        if (string == null) {
            return null;
        }
        String[] split = string.split(":");
        String name;
        String itemId;
        try {
            name = split[0];
            itemId = String.join(":", Arrays.copyOfRange(split, 1, split.length));
        } catch (ArrayIndexOutOfBoundsException exception) {
            Loggers.warn(ItemAddon.class, "Failed to process an ItemAddon String! \"" + string + "\" is not formatted correctly.", new InvalidItemException());
            return null;
        }
        ItemAddon addon = get(name);
        if (addon == null) {
            Loggers.warn(ItemAddon.class, "Failed to process an ItemAddon String! \"" + name + "\" is not a valid ItemAddon.", new InvalidAddonException());
            return null;
        }
        return addon.getItem(itemId);
    }

    /**
     * Checks if this registry is empty.
     *
     * @return Whether this registry is empty.
     */
    @Override
    public boolean isEmpty() {
        return registry.isEmpty();
    }

    /**
     * Removes all items from this registry.
     */
    @Override
    public void clear() {
        registry.clear();
    }

}

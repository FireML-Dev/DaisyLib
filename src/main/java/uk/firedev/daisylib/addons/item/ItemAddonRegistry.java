package uk.firedev.daisylib.addons.item;

import net.kyori.adventure.key.Key;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.addons.InvalidAddonException;
import uk.firedev.daisylib.registry.Registry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ItemAddonRegistry implements Registry<ItemAddon> {

    private static final ItemAddonRegistry instance = new ItemAddonRegistry();

    private final Map<String, ItemAddon> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private ItemAddonRegistry() {}

    public static @NotNull ItemAddonRegistry get() {
        return instance;
    }

    @NotNull
    @Override
    public Map<String, ItemAddon> getRegistry() {
        return Map.copyOf(registry);
    }

    @Nullable
    @Override
    public ItemAddon get(@NotNull String key) {
        return registry.get(key);
    }

    @NotNull
    @Override
    public ItemAddon getOrDefault(@NotNull String key, @NotNull ItemAddon defaultValue) {
        return registry.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean unregister(@NotNull String key) {
        return registry.remove(key) != null;
    }

    @Override
    public boolean register(@NotNull ItemAddon value, boolean force) {
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

}

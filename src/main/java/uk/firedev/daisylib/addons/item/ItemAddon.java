package uk.firedev.daisylib.addons.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.addons.Addon;
import uk.firedev.daisylib.addons.InvalidAddonException;

import java.util.Map;
import java.util.TreeMap;

public abstract class ItemAddon extends Addon {

    private static final TreeMap<String, ItemAddon> loadedAddons = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public static Map<String, ItemAddon> getLoadedAddons() {
        return Map.copyOf(loadedAddons);
    }

    public static @Nullable ItemAddon get(@NotNull String identifier) {
        return loadedAddons.get(identifier);
    }

    public static boolean unregister(@NotNull String identifier) {
        if (!loadedAddons.containsKey(identifier)) {
            return false;
        }
        loadedAddons.remove(identifier);
        return true;
    }

    public static @Nullable ItemStack processString(@Nullable String string) {
        if (string == null) {
            return null;
        }
        String[] split = string.split("=");
        String name;
        String itemId;
        try {
            name = split[0];
            itemId = split[1];
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

    public ItemAddon() {}

    public abstract ItemStack getItem(@NotNull String id);

    public boolean register() {
        if (loadedAddons.containsKey(getIdentifier())) {
            return false;
        }
        loadedAddons.put(getIdentifier(), this);
        return true;
    }

    public boolean unregister() {
        return unregister(getIdentifier());
    }

}

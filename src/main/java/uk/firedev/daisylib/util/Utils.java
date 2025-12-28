package uk.firedev.daisylib.util;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * A very large class containing a lot of utility methods.
 */
public class Utils {

    /**
     * Checks if a String is a valid Integer.
     * @param str The String to check.
     * @return Is the String an Integer?
     */
    public static boolean isInt(@NotNull String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Gets an Integer from a String
     * @param str The string to use.
     * @return The Integer, or null if it isn't an Integer.
     */
    public static @Nullable Integer getInt(@NotNull String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Gets an int from a String or returns the default
     * @param str The string to use.
     * @return The int, or the default value if it isn't an int.
     */
    public static int getIntOrDefault(@Nullable String str, int defaultInt) {
        if (str == null) {
            return defaultInt;
        }
        Integer value = getInt(str);
        if (value == null) {
            return defaultInt;
        }
        return value;
    }

    /**
     * Checks if a String is a valid Long.
     * @param str The String to check.
     * @return Is the String a Long?
     */
    public static boolean isLong(@NotNull String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Gets a Long from a String
     * @param str The string to use.
     * @return The Long, or null if it isn't a Long.
     */
    public static @Nullable Long getLong(@NotNull String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Gets a long from a String or returns the default
     * @param str The string to use.
     * @return The long, or the default value if it isn't a long.
     */
    public static long getLongOrDefault(@Nullable String str, long defaultLong) {
        if (str == null) {
            return defaultLong;
        }
        Long value = getLong(str);
        if (value == null) {
            return defaultLong;
        }
        return value;
    }

    /**
     * Checks if a String is a valid Double.
     * @param str The String to check.
     * @return Is the String a Double?
     */
    public static boolean isDouble(@NotNull String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Gets a Double from a String
     * @param str The string to use.
     * @return The Double, or null if it isn't a Double.
     */
    public static @Nullable Double getDouble(@NotNull String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Gets a double from a String or returns the default
     * @param str The string to use.
     * @return The double, or the default value if it isn't a double.
     */
    public static double getDoubleOrDefault(@Nullable String str, double defaultDouble) {
        if (str == null) {
            return defaultDouble;
        }
        Double value = getDouble(str);
        if (value == null) {
            return defaultDouble;
        }
        return value;
    }

    /**
     * Checks if a String is a valid Float.
     * @param str The String to check.
     * @return Is the String a Float?
     */
    public static boolean isFloat(@NotNull String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Gets a Float from a String
     * @param str The string to use.
     * @return The Float, or null if it isn't a Float.
     */
    public static @Nullable Float getFloat(@NotNull String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Gets a float from a String or returns the default
     * @param str The string to use.
     * @return The float, or the default value if it isn't a float.
     */
    public static float getFloatOrDefault(@Nullable String str, float defaultFloat) {
        if (str == null) {
            return defaultFloat;
        }
        Float value = getFloat(str);
        if (value == null) {
            return defaultFloat;
        }
        return value;
    }

    /**
     * Gets the provided index from an array, or returns the default value.
     * @param array The array to use.
     * @param index The index to return.
     * @param def The default value.
     * @return The provided index, or the default value.
     */
    public static <T> T getOrDefault(@NotNull T[] array, int index, T def) {
        try {
            return array[index];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return def;
        }
    }

    /**
     * Gets the provided index from a list, or returns the default value.
     * @param list The list to use.
     * @param index The index to return.
     * @param def The default value.
     * @return The provided index, or the default value.
     */
    public static <T> T getOrDefault(@NotNull List<T> list, int index, T def) {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException ex) {
            return def;
        }
    }

    public static @NotNull <E extends Enum<E>> E getEnumValue(@NotNull Class<E> enumClass, @Nullable String value, @NotNull E def) {
        E enumValue = getEnumValue(enumClass, value);
        if (enumValue == null) {
            return def;
        }
        return enumValue;
    }

    public static @Nullable <E extends Enum<E>> E getEnumValue(@NotNull Class<E> enumClass, @Nullable String value) {
        if (value == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    /**
     * @apiNote This method can be used asynchronously.
     */
    public static boolean randomChance(double chance) {
        if (chance < 0) {
            return false;
        }
        if (chance >= 100) {
            return true;
        }
        return chance > ThreadLocalRandom.current().nextDouble(100);
    }

    /**
     * Gets the first Character from a given String
     *
     * @param string      The String to use.
     * @param defaultChar The default character to use if an exception is thrown.
     * @return The first Character from the String
     */
    public static char getCharFromString(@NotNull String string, char defaultChar) {
        try {
            return string.toCharArray()[0];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return defaultChar;
        }
    }

    public static @Nullable ItemStack getItem(@Nullable String itemName) {
        ItemType itemType = getItemType(itemName);
        if (itemType != null) {
            return itemType.createItemStack();
        }
        // TODO item addons yippee
        return null; // ItemAddonRegistry.get().processString(itemName);
    }

    public static @NotNull ItemStack getItem(@Nullable String itemName, @NotNull ItemStack defaultItem) {
        ItemStack item = getItem(itemName);
        return item == null ? defaultItem : item;
    }

    public static @Nullable ItemType getItemType(@Nullable String itemName) {
        return getFromRegistry(
            itemName,
            RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM)
        );
    }

    public static @NotNull ItemType getItemType(@Nullable String itemName, @NotNull ItemType defaultType) {
        ItemType type = getItemType(itemName);
        return type == null ? defaultType : type;
    }

    public static @Nullable Enchantment getEnchantment(@Nullable String enchantmentName) {
        return getFromRegistry(
            enchantmentName,
            RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT)
        );
    }

    public static <T extends Keyed> @Nullable T getFromRegistry(@Nullable String name, @NotNull Registry<T> registry) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        NamespacedKey key = NamespacedKey.fromString(name.toLowerCase());
        if (key == null) {
            return null;
        }
        return registry.get(key);
    }

    public static <E extends Entity> void modifyEntity(@NotNull Entity entity, @NotNull Class<E> classCheck, @NotNull Consumer<? super E> consumer) {
        if (classCheck.isInstance(entity)) {
            E checked = classCheck.cast(entity);
            consumer.accept(checked);
        }
    }

    public static @Nullable BlockType getBlockType(@Nullable String blockName) {
        return getFromRegistry(
            blockName,
            RegistryAccess.registryAccess().getRegistry(RegistryKey.BLOCK)
        );
    }

    public static @NotNull BlockType getBlockType(@Nullable String blockName, @NotNull BlockType defaultType) {
        BlockType type = getBlockType(blockName);
        return type == null ? defaultType : type;
    }

    /**
     * Checks if a location is spawnable
     * @param location The location to check
     * @return Whether the location is spawnable
     */
    public static boolean isSpawnable(@NotNull Location location) {
        return location.getBlock().isPassable() && getAbove(location).isPassable() && getBelow(location).getType().isSolid();
    }

    /**
     * Gets the block below the provided block.
     * @param block The block to use.
     * @return The block below the provided block.
     */
    public static Block getBelow(@NotNull Block block) {
        return block.getRelative(0, -1, 0);
    }

    /**
     * Gets the block below the provided location.
     * @param location The location to use.
     * @return The block below the provided location.
     */
    public static Block getBelow(@NotNull Location location) {
        return getBelow(location.getBlock());
    }

    /**
     * Gets the block above the provided block.
     * @param block The block to use.
     * @return The block above the provided block.
     */
    public static Block getAbove(@NotNull Block block) {
        return block.getRelative(0, 1, 0);
    }

    /**
     * Gets the block above the provided location.
     * @param location The location to use.
     * @return The block above the provided location.
     */
    public static Block getAbove(@NotNull Location location) {
        return getAbove(location.getBlock());
    }

    /**
     * Adds a location to a config file.
     * It is recommended to save the provided YamlDocument after calling this method.
     * @param config The config file to use.
     * @param path The path to set the location at.
     * @param location The location to add.
     */
    public static void addLocationToConfig(@NotNull YamlConfiguration config, @NotNull String path, @NotNull Location location) {
        config.set(path, location);
    }

    /**
     * Gets a location from a config file.
     * @param config The config file to use.
     * @param path The path to get the values from.
     * @return The location, or null if it is invalid.
     */
    public static @Nullable Location getLocationFromConfig(@NotNull YamlConfiguration config, @NotNull String path) {
        return config.getLocation(path);
    }

}

package uk.firedev.daisylib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.List;

public class ObjectUtils {

    /**
     * Creates a String from the provided chunk.
     * @param chunk The chunk to use.
     * @return A string, built from the provided chunk.
     */
    public static String chunkToString(@NotNull Chunk chunk) {
        return chunk.getWorld().getName() + "_" + chunk.getX() + "_" + chunk.getZ();
    }

    /**
     * Creates a Chunk from a String created by {@link #chunkToString(Chunk)}
     * @param string The string to use.
     * @return A chunk, built from the provided string.
     */
    public static Chunk chunkFromString(@NotNull String string) {
        String[] split = string.split("_");
        if (split.length < 3) {
            return null;
        }
        World world = Bukkit.getWorld(split[0]);
        if (world == null) {
            return null;
        }
        int x = Integer.parseInt(split[1]);
        int z = Integer.parseInt(split[2]);
        return world.getChunkAt(x, z);
    }

    /**
     * Creates a NamespacedKey with the provided values.
     * @param key The value of the key. Must not be null.
     * @param plugin The plugin to create the key. Defaults to DaisyLib if null.
     * @return A NamespacedKey instance with the given key and plugin.
     */
    public static NamespacedKey createNamespacedKey(@NotNull String key, @Nullable JavaPlugin plugin) {
        if (plugin == null) {
            return new NamespacedKey(DaisyLib.getInstance(), key);
        }
        return new NamespacedKey(plugin, key);
    }

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
        if (isInt(str)) {
            return Integer.parseInt(str);
        }
        return null;
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
        if (isLong(str)) {
            return Long.parseLong(str);
        }
        return null;
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
        if (isDouble(str)) {
            return Double.parseDouble(str);
        }
        return null;
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
        if (isFloat(str)) {
            return Float.parseFloat(str);
        }
        return null;
    }

    /**
     * Gets the provided index from an array, or returns the default value.
     * @param array The array to use.
     * @param index The index to return.
     * @param def The default value.
     * @return The provided index, or the default value.
     */
    @SuppressWarnings("UnreachableCode")
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

}

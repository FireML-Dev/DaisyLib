package uk.firedev.daisylib.utils;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.List;

public class ObjectUtils {

    /**
     * @deprecated Use {@link LocationHelper#convertToString(Location, boolean)} instead.
     * Creates a String from the provided Location.
     * @param location The location to use.
     * @param includeYawPitch Should the String include yaw and pitch?
     * @return A string, built from the provided location.
     */
    @Deprecated(forRemoval = true)
    public static String locationToString(@NotNull Location location, boolean includeYawPitch) {
        String finalString = location.getWorld().getName() + "_" + location.getX() + "_" + location.getY() + "_" + location.getZ();
        if (includeYawPitch) {
            finalString += "_" + location.getYaw() + "_" + location.getPitch();
        }
        return finalString;
    }

    /**
     * @deprecated Use {@link LocationHelper#getFromString(String)} instead.
     * Creates a Location from a String created by {@link #locationToString(Location, boolean)}
     * @param string The string to use.
     * @return A location, built from the provided string.
     */
    @Deprecated(forRemoval = true)
    public static Location locationFromString(@NotNull String string) {
        String[] split = string.split("_");
        if (split.length < 4) {
            return null;
        }
        World world = Bukkit.getWorld(split[0]);
        if (world == null) {
            return null;
        }
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = Float.parseFloat(getOrDefault(split, 4, String.valueOf(180)));
        float pitch = Float.parseFloat(getOrDefault(split, 4, String.valueOf(0)));
        return new Location(world, x, y, z, yaw, pitch);
    }

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

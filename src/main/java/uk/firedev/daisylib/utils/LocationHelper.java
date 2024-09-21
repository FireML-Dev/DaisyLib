package uk.firedev.daisylib.utils;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;

public class LocationHelper {

    private static final String separator = ";;";

    public static boolean isSpawnable(@NotNull Location location) {
        return location.getBlock().isPassable() && getAbove(location).isPassable() && getBelow(location).getType().isSolid();
    }

    public static Block getBelow(@NotNull Block block) {
        World world = block.getWorld();
        int x = block.getX();
        int y = block.getY() - 1;
        int z = block.getZ();
        return world.getBlockAt(x, y, z);
    }

    public static Block getBelow(@NotNull Location location) {
        return getBelow(location.getBlock());
    }

    public static Block getAbove(@NotNull Block block) {
        World world = block.getWorld();
        int x = block.getX();
        int y = block.getY() + 1;
        int z = block.getZ();
        return world.getBlockAt(x, y, z);
    }

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
    public static void addToConfig(@NotNull YamlDocument config, @NotNull String path, @NotNull Location location) {
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());
    }

    /**
     * Gets a location from a config file.
     * @param config The config file to use.
     * @param path The path to get the values from.
     * @return The location, or null if it is invalid.
     */
    public static @Nullable Location getFromConfig(@NotNull YamlDocument config, @NotNull String path) {
        String worldName = config.getString(path + ".world");
        if (worldName == null) {
            return null;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }

        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = config.getFloat(path + ".yaw");
        float pitch = config.getFloat(path + ".pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * Creates a String from the provided Location.
     * @param location The location to use.
     * @param includeYawPitch Should the String include yaw and pitch?
     * @return A string, built from the provided location.
     */
    public static String convertToString(@NotNull Location location, boolean includeYawPitch) {
        String finalString = location.getWorld().getName() + separator + location.getX() + separator + location.getY() + separator + location.getZ();
        if (includeYawPitch) {
            finalString += separator + location.getYaw() + separator + location.getPitch();
        }
        return finalString;
    }

    /**
     * Creates a Location from a String created by {@link #convertToString(Location, boolean)}
     * @param string The string to use.
     * @return A location, built from the provided string.
     */
    public static Location getFromString(@NotNull String string) {
        String[] split = string.split(separator);
        if (split.length < 4) {
            return null;
        }
        World world = Bukkit.getWorld(split[0]);
        if (world == null) {
            return null;
        }
        try {
            double x = Double.parseDouble(split[1]);
            double y = Double.parseDouble(split[2]);
            double z = Double.parseDouble(split[3]);
            float yaw = Float.parseFloat(ObjectUtils.getOrDefault(split, 4, String.valueOf(180)));
            float pitch = Float.parseFloat(ObjectUtils.getOrDefault(split, 5, String.valueOf(0)));
            return new Location(world, x, y, z, yaw, pitch);
        } catch (NumberFormatException ex) {
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Failed to parse a Location from a String.", ex);
            return null;
        }
    }

}

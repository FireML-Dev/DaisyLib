package uk.firedev.daisylib.api.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LocationHelper {

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
    public static void addToConfig(@NotNull YamlConfiguration config, @NotNull String path, @NotNull Location location) {
        config.set(path, location);
    }

    /**
     * Gets a location from a config file.
     * @param config The config file to use.
     * @param path The path to get the values from.
     * @return The location, or null if it is invalid.
     */
    public static @Nullable Location getFromConfig(@NotNull YamlConfiguration config, @NotNull String path) {
        return config.getLocation(path);
    }

}

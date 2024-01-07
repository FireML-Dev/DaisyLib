package uk.firedev.daisylib.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import uk.firedev.daisylib.local.DaisyLib;

public class BlockUtils {

    /**
     * Check if a block was placed by a player
     */
    public static boolean isPlayerPlaced(Location location) {
        if (location == null) {
            return true;
        }
        Chunk chunk = location.getChunk();
        String locationString = ObjectUtils.locationToString(location, false);
        NamespacedKey key = ObjectUtils.createNamespacedKey("blockplaced-" + locationString, null);
        return chunk.getPersistentDataContainer().has(key);
    }

    /**
     * Check if a block was broken by a player
     */
    public static boolean isPlayerBroken(Location location) {
        if (location == null) {
            return true;
        }
        Chunk chunk = location.getChunk();
        String locationString = ObjectUtils.locationToString(location, false);
        NamespacedKey key = ObjectUtils.createNamespacedKey("blockbroken-" + locationString, null);
        return chunk.getPersistentDataContainer().has(key);
    }

    public static void applyBreak(Location location) {
        if (location == null) {
            return;
        }
        if (isPlayerPlaced(location)) {
            Chunk chunk = location.getChunk();
            String locationString = ObjectUtils.locationToString(location, false);
            NamespacedKey brokenKey = ObjectUtils.createNamespacedKey("blockbroken-" + locationString, null);
            if (!isPlayerBroken(location)) {
                chunk.getPersistentDataContainer().set(brokenKey, PersistentDataType.BOOLEAN, true);
            }
        }
    }

    public static void removeBreak(Location location) {
        if (location == null) {
            return;
        }
        Chunk chunk = location.getChunk();
        String locationString = ObjectUtils.locationToString(location, false);
        NamespacedKey brokenKey = ObjectUtils.createNamespacedKey("blockbroken-" + locationString, null);
        if (chunk.getPersistentDataContainer().has(brokenKey)) {
            chunk.getPersistentDataContainer().remove(brokenKey);
        }
    }

    public static void applyPlace(Location location) {
        if (location == null) {
            return;
        }
        Chunk chunk = location.getChunk();
        String locationString = ObjectUtils.locationToString(location, false);
        NamespacedKey placedKey = ObjectUtils.createNamespacedKey("blockplaced-" + locationString, null);
        if (!isPlayerPlaced(location)) {
            chunk.getPersistentDataContainer().set(placedKey, PersistentDataType.BOOLEAN, true);
        }
    }

    public static void removePlace(Location location) {
        if (location == null) {
            return;
        }
        Chunk chunk = location.getChunk();
        String locationString = ObjectUtils.locationToString(location, false);
        NamespacedKey placedKey = ObjectUtils.createNamespacedKey("blockplaced-" + locationString, null);
        if (chunk.getPersistentDataContainer().has(placedKey)) {
            chunk.getPersistentDataContainer().remove(placedKey);
        }
    }

}

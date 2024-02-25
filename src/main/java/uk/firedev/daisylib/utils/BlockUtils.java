package uk.firedev.daisylib.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;
import uk.firedev.daisylib.local.config.ConfigManager;

public class BlockUtils implements Listener {

    /**
     * Check if a block was placed by a player
     */
    public static boolean isPlayerPlaced(Location location) {
        if (location == null) {
            return true;
        }
        Chunk chunk = location.getChunk();
        NamespacedKey key = ObjectUtils.createNamespacedKey("blockplaced-" + ObjectUtils.locationToString(location, false), null);
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
        NamespacedKey key = ObjectUtils.createNamespacedKey("blockbroken-" + ObjectUtils.locationToString(location, false), null);
        return chunk.getPersistentDataContainer().has(key);
    }

    public static void applyBreak(Location location) {
        if (location == null) {
            return;
        }
        if (isPlayerPlaced(location)) {
            Chunk chunk = location.getChunk();
            NamespacedKey brokenKey = ObjectUtils.createNamespacedKey("blockbroken-" + ObjectUtils.locationToString(location, false), null);
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
        NamespacedKey brokenKey = ObjectUtils.createNamespacedKey("blockbroken-" + ObjectUtils.locationToString(location, false), null);
        if (chunk.getPersistentDataContainer().has(brokenKey)) {
            chunk.getPersistentDataContainer().remove(brokenKey);
        }
    }

    public static void applyPlace(Location location) {
        if (location == null) {
            return;
        }
        Chunk chunk = location.getChunk();
        NamespacedKey placedKey = ObjectUtils.createNamespacedKey("blockplaced-" + ObjectUtils.locationToString(location, false), null);
        if (!isPlayerPlaced(location)) {
            chunk.getPersistentDataContainer().set(placedKey, PersistentDataType.BOOLEAN, true);
        }
    }

    public static void removePlace(Location location) {
        if (location == null) {
            return;
        }
        Chunk chunk = location.getChunk();
        NamespacedKey placedKey = ObjectUtils.createNamespacedKey("blockplaced-" + ObjectUtils.locationToString(location, false), null);
        if (chunk.getPersistentDataContainer().has(placedKey)) {
            chunk.getPersistentDataContainer().remove(placedKey);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent e) {
        if (ConfigManager.getInstance().doPlaceBreak()) {
            applyPlace(e.getBlockPlaced().getLocation());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent e) {
        if (ConfigManager.getInstance().doPlaceBreak()) {
            applyBreak(e.getBlock().getLocation());
        }
    }

}

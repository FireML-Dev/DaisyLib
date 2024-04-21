package uk.firedev.daisylib.utils;

import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.local.config.MainConfig;

public class BlockUtils implements Listener {

    /**
     * Check if a block was placed by a player
     */
    public static boolean isPlayerPlaced(@NotNull Block block) {
        Chunk chunk = block.getChunk();
        NamespacedKey key = ObjectUtils.createNamespacedKey("blockplaced-" + ObjectUtils.locationToString(block.getLocation(), false), null);
        return chunk.getPersistentDataContainer().has(key);
    }

    /**
     * Check if a block was broken by a player
     */
    public static boolean isPlayerBroken(@NotNull Block block) {
        Chunk chunk = block.getChunk();
        NamespacedKey key = ObjectUtils.createNamespacedKey("blockbroken-" + ObjectUtils.locationToString(block.getLocation(), false), null);
        return chunk.getPersistentDataContainer().has(key);
    }

    public static void applyBreak(@NotNull Block block) {
        if (isPlayerPlaced(block) && !isPlayerBroken(block)) {
            Chunk chunk = block.getChunk();
            NamespacedKey brokenKey = ObjectUtils.createNamespacedKey("blockbroken-" + ObjectUtils.locationToString(block.getLocation(), false), null);
            chunk.getPersistentDataContainer().set(brokenKey, PersistentDataType.BOOLEAN, true);
        }
    }

    public static void removeBreak(@NotNull Block block) {
        Chunk chunk = block.getChunk();
        NamespacedKey brokenKey = ObjectUtils.createNamespacedKey("blockbroken-" + ObjectUtils.locationToString(block.getLocation(), false), null);
        if (chunk.getPersistentDataContainer().has(brokenKey)) {
            chunk.getPersistentDataContainer().remove(brokenKey);
        }
    }

    public static void applyPlace(@NotNull Block block) {
        Chunk chunk = block.getChunk();
        NamespacedKey placedKey = ObjectUtils.createNamespacedKey("blockplaced-" + ObjectUtils.locationToString(block.getLocation(), false), null);
        if (!isPlayerPlaced(block)) {
            chunk.getPersistentDataContainer().set(placedKey, PersistentDataType.BOOLEAN, true);
        }
    }

    public static void removePlace(@NotNull Block block) {
        Chunk chunk = block.getChunk();
        NamespacedKey placedKey = ObjectUtils.createNamespacedKey("blockplaced-" + ObjectUtils.locationToString(block.getLocation(), false), null);
        if (chunk.getPersistentDataContainer().has(placedKey)) {
            chunk.getPersistentDataContainer().remove(placedKey);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent e) {
        if (MainConfig.getInstance().doPlaceBreak()) {
            applyPlace(e.getBlockPlaced());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent e) {
        if (MainConfig.getInstance().doPlaceBreak()) {
            applyBreak(e.getBlock());
        }
    }

}

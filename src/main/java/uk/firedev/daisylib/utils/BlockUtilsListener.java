package uk.firedev.daisylib.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import uk.firedev.daisylib.local.config.ConfigManager;

public class BlockUtilsListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent e) {
        if (ConfigManager.getInstance().getConfig().getBoolean("config.enablePlaceBreak")) {
            BlockUtils.applyPlace(e.getBlockPlaced().getLocation());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent e) {
        if (ConfigManager.getInstance().getConfig().getBoolean("config.enablePlaceBreak")) {
            BlockUtils.applyBreak(e.getBlock().getLocation());
        }
    }

}

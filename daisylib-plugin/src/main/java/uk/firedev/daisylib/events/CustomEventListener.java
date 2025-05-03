package uk.firedev.daisylib.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import uk.firedev.daisylib.local.config.MainConfig;

public class CustomEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMoveBlock(PlayerMoveEvent e) {
        if (MainConfig.getInstance().doMoveBlockEvent() && e.getFrom().getBlockX() != e.getTo().getBlockX() && e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            PlayerMoveBlockEvent pmbe = new PlayerMoveBlockEvent(e.getFrom(), e.getTo(), e.getPlayer());
            Bukkit.getServer().getPluginManager().callEvent(pmbe);
            if (pmbe.isCancelled()) {
                e.setCancelled(true);
            }
        }
        if (MainConfig.getInstance().doMoveChunkEvent() && e.getFrom().getChunk() != e.getTo().getChunk()) {
            PlayerMoveChunkEvent pmce = new PlayerMoveChunkEvent(e.getFrom().getChunk(), e.getTo().getChunk(), e.getPlayer());
            Bukkit.getServer().getPluginManager().callEvent(pmce);
            if (pmce.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }

}

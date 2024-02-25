package uk.firedev.daisylib.events;

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import uk.firedev.daisylib.local.config.ConfigManager;

public class CustomEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMoveBlock(PlayerMoveEvent e) {
        if (ConfigManager.getInstance().doMoveBlockEvent() && e.getFrom().getBlockX() != e.getTo().getBlockX() && e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            PlayerMoveBlockEvent pmbe = new PlayerMoveBlockEvent(e.getFrom(), e.getTo(), e.getPlayer());
            Bukkit.getServer().getPluginManager().callEvent(pmbe);
            if (pmbe.isCancelled()) {
                e.setCancelled(true);
            }
        }
        if (ConfigManager.getInstance().doMoveChunkEvent() && e.getFrom().getChunk() != e.getTo().getChunk()) {
            PlayerMoveChunkEvent pmce = new PlayerMoveChunkEvent(e.getFrom().getChunk(), e.getTo().getChunk(), e.getPlayer());
            Bukkit.getServer().getPluginManager().callEvent(pmce);
            if (pmce.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPhantomPreSpawn(PhantomPreSpawnEvent e) {
        if (!new CreaturePreSpawnEvent(e.getType(), e.getSpawnLocation(), e.getReason()).callEvent()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCreaturePreSpawn(PreCreatureSpawnEvent e) {
        if (!new CreaturePreSpawnEvent(e.getType(), e.getSpawnLocation(), e.getReason()).callEvent()) {
            e.setCancelled(true);
        }
    }

}

package uk.firedev.daisylib.events.move;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class MoveEventListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location to = event.getTo();
        Location from = event.getFrom();
        // Block check
        if (!to.getBlock().equals(from.getBlock())) {
            if (!new PlayerMoveBlockEvent(event.getPlayer(), to, from).callEvent()) {
                event.setCancelled(true);
                return;
            }
        }
        // Chunk check
        if (!to.getChunk().equals(from.getChunk())) {
            if (!new PlayerMoveChunkEvent(event.getPlayer(), to, from).callEvent()) {
                event.setCancelled(true);
            }
        }
    }

}

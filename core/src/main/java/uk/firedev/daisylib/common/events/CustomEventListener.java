package uk.firedev.daisylib.common.events;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.ApiStatus;
import uk.firedev.daisylib.common.events.move.PlayerMoveBlockEvent;
import uk.firedev.daisylib.common.events.move.PlayerMoveChunkEvent;

@ApiStatus.Internal
public class CustomEventListener implements Listener {

    @EventHandler
    public void onBlockChange(EntityChangeBlockEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!(event.getBlock().getState() instanceof BrushableBlock brushable)) {
            return;
        }
        // Invert the result as false means cancelled.
        boolean cancelled = !new BlockBrushEvent(player, brushable).callEvent();
        event.setCancelled(cancelled);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location to = event.getTo();
        Location from = event.getFrom();
        // Block check
        if (!to.getBlock().equals(from.getBlock())) {
            new PlayerMoveBlockEvent(event.getPlayer(), to, from).callEvent();
        }
        // Chunk check
        if (!to.getChunk().equals(from.getChunk())) {
            new PlayerMoveChunkEvent(event.getPlayer(), to, from).callEvent();
        }
    }

}

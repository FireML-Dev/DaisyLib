package uk.firedev.daisylib.event;

import org.bukkit.block.BrushableBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.jetbrains.annotations.ApiStatus;

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
        boolean cancelled = !new BlockPreBrushEvent(player, brushable).callEvent();
        event.setCancelled(cancelled);
    }

}

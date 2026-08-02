package uk.firedev.daisylib.events;

import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.blockstate.CustomBrushable;

/**
 * Called when a player brushes a BrushableBlock (such as Suspicious Sand).
 */
public class BlockBrushEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final CustomBrushable customBrushable;

    private boolean cancelled = false;

    public BlockBrushEvent(@NonNull Player player, @NonNull BrushableBlock brushable) {
        super(player);
        this.customBrushable = CustomBrushable.customBrushable(brushable);
    }

    public @NonNull BrushableBlock getState() {
        return customBrushable.getState();
    }

    public @NonNull Block getBlock() {
        return customBrushable.getBlock();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

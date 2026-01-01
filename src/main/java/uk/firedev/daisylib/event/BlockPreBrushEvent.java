package uk.firedev.daisylib.event;

import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.blockstate.CustomBrushable;

public class BlockPreBrushEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final CustomBrushable customBrushable;

    private boolean cancelled = false;

    public BlockPreBrushEvent(@NotNull Player player, @NotNull BrushableBlock brushable) {
        super(player);
        this.customBrushable = CustomBrushable.customBrushable(brushable);
    }

    public @NotNull BrushableBlock getState() {
        return customBrushable.getState();
    }

    public @NotNull Block getBlock() {
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
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

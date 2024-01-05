package uk.firedev.daisylib.events;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerMoveChunkEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Chunk from;
    private final Chunk to;
    private boolean cancelled = false;

    public PlayerMoveChunkEvent(Chunk from, Chunk to, Player player) {
        super(player);
        this.from = from;
        this.to = to;
    }

    public Chunk getFrom() {
        return from;
    }

    public Chunk getTo() {
        return to;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(final boolean cancel) {
        cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
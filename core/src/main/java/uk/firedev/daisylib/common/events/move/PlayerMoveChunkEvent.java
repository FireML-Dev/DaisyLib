package uk.firedev.daisylib.common.events.move;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * Called when a player moves between chunks.
 */
public class PlayerMoveChunkEvent extends AbstractMoveEvent {

    private static final HandlerList handlers = new HandlerList();

    private final Chunk to;
    private final Chunk from;

    public PlayerMoveChunkEvent(@NonNull Player player, @NonNull Location to, @NonNull Location from) {
        super(player, to, from);
        this.to = to.getChunk();
        this.from = from.getChunk();
    }

    public @NonNull Chunk getTo() {
        return this.to;
    }

    public @NonNull Chunk getFrom() {
        return this.from;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

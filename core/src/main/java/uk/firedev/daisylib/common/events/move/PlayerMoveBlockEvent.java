package uk.firedev.daisylib.common.events.move;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

/**
 * Called when a player moves between blocks.
 */
public class PlayerMoveBlockEvent extends AbstractMoveEvent {

    private static final HandlerList handlers = new HandlerList();

    private final Block to;
    private final Block from;

    public PlayerMoveBlockEvent(@NonNull Player player, @NonNull Location to, @NonNull Location from) {
        super(player, to, from);
        this.to = to.getBlock();
        this.from = from.getBlock();
    }

    public @NonNull Block getTo() {
        return this.to;
    }

    public @NonNull Block getFrom() {
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

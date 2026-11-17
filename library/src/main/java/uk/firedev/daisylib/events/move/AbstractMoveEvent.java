package uk.firedev.daisylib.events.move;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;

public abstract class AbstractMoveEvent extends PlayerEvent implements Cancellable {

    private final Location to;
    private final Location from;

    protected boolean cancelled = false;

    @ApiStatus.Internal
    public AbstractMoveEvent(@NonNull Player player, @NonNull Location to, @NonNull Location from) {
        super(player);
        this.to = to;
        this.from = from;
    }

    public @NonNull Location getToLocation() {
        return this.to;
    }

    public @NonNull Location getFromLocation() {
        return this.from;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

}

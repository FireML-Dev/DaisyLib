package uk.firedev.daisylib.common.events.move;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;

@ApiStatus.Internal
public abstract class AbstractMoveEvent extends PlayerEvent {

    private final Location to;
    private final Location from;

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

}

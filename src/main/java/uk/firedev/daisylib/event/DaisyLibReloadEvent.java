package uk.firedev.daisylib.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class DaisyLibReloadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public @NonNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

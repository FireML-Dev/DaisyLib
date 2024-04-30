package uk.firedev.daisylib.events;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;

public class CreaturePreSpawnEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final EntityType entityType;
    private final Location location;
    private final CreatureSpawnEvent.SpawnReason spawnReason;
    private boolean cancelled = false;

    public CreaturePreSpawnEvent(@NotNull EntityType entityType, @NotNull Location location, @NotNull CreatureSpawnEvent.SpawnReason spawnReason) {
        this.entityType = entityType;
        this.location = location;
        this.spawnReason = spawnReason;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Location getLocation() {
        return location;
    }

    public CreatureSpawnEvent.SpawnReason getSpawnReason() {
        return spawnReason;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

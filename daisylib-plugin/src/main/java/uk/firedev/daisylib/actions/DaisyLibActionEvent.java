package uk.firedev.daisylib.actions;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DaisyLibActionEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final ActionType type;
    private final ActionContext context;

    public DaisyLibActionEvent(@NotNull ActionType type, @NotNull ActionContext context) {
        this.type = type;
        this.context = context;
    }

    public @NotNull ActionType getActionType() {
        return type;
    }

    public @NotNull ActionContext getActionContext() {
        return context;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

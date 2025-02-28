package uk.firedev.daisylib.actions;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public abstract class ActionType implements Listener {

    public void register() {
        ActionManager.getInstance().registerActionType(this);
    }

    public abstract @NotNull Plugin getPlugin();

    public abstract @NotNull String getActionIdentifier();

    public void fire(@NotNull ActionContext context) {
        ActionManager.getInstance().fire(this, context);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActionType cast)) {
            return false;
        }
        return getActionIdentifier().equalsIgnoreCase(cast.getActionIdentifier());
    }

    @Override
    public int hashCode() {
        return getActionIdentifier().toLowerCase().hashCode();
    }

}

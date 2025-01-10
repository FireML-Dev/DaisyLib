package uk.firedev.daisylib.actions;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public abstract class ActionType implements Listener {

    public void register(@NotNull Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public abstract @NotNull String getActionIdentifier();

    public void fire(@NotNull ActionContext context) {
        ActionManager.getInstance().fire(getActionIdentifier(), context);
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

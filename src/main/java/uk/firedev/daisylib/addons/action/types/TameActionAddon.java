package uk.firedev.daisylib.addons.action.types;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class TameActionAddon extends ActionAddon<EntityTameEvent> implements Listener {

    @NonNull
    @Override
    public Class<EntityTameEvent> getEventType() {
        return EntityTameEvent.class;
    }

    @NonNull
    @Override
    public String getKey() {
        return "tame";
    }

    @EventHandler(ignoreCancelled = true)
    public void onTame(EntityTameEvent event) {
        if (!(event.getOwner() instanceof Player)) {
            return;
        }
        fireEvent(event, event.getEntityType().toString());
    }

    @NonNull
    @Override
    public Plugin getPlugin() {
        return DaisyLibPlugin.getInstance();
    }

    @NonNull
    @Override
    public String getAuthor() {
        return "FireML";
    }

}

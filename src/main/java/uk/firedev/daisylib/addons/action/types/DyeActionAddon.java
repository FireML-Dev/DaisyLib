package uk.firedev.daisylib.addons.action.types;

import io.papermc.paper.event.entity.EntityDyeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class DyeActionAddon extends ActionAddon<EntityDyeEvent> implements Listener {

    @NonNull
    @Override
    public Class<EntityDyeEvent> getEventType() {
        return EntityDyeEvent.class;
    }

    @NonNull
    @Override
    public String getKey() {
        return "dye";
    }

    @EventHandler(ignoreCancelled = true)
    public void onDye(EntityDyeEvent event) {
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

package uk.firedev.daisylib.addons.action.types;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class BreedActionAddon extends ActionAddon<EntityBreedEvent> implements Listener {

    @NonNull
    @Override
    public Class<EntityBreedEvent> getEventType() {
        return EntityBreedEvent.class;
    }

    @NonNull
    @Override
    public String getKey() {
        return "breed";
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreed(EntityBreedEvent event) {
        if (!(event.getBreeder() instanceof Player)) {
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

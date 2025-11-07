package uk.firedev.daisylib.addons.action.types;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;

public class BreedActionAddon extends ActionAddon<EntityBreedEvent> implements Listener {

    @NotNull
    @Override
    public Class<EntityBreedEvent> getEventType() {
        return EntityBreedEvent.class;
    }

    @NotNull
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

}

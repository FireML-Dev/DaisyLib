package uk.firedev.daisylib.addons.action.types;

import io.papermc.paper.event.entity.EntityDyeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.local.DaisyLib;

public class DyeActionAddon extends ActionAddon<EntityDyeEvent> implements Listener {

    @NotNull
    @Override
    public Class<EntityDyeEvent> getEventType() {
        return EntityDyeEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "dye";
    }

    @EventHandler(ignoreCancelled = true)
    public void onDye(EntityDyeEvent event) {
        fireEvent(event, event.getEntityType().toString());
    }

    @NotNull
    @Override
    public Plugin getPlugin() {
        return DaisyLib.getInstance();
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "FireML";
    }

}

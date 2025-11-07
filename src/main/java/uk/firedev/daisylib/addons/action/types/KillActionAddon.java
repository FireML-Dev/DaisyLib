package uk.firedev.daisylib.addons.action.types;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.local.DaisyLib;

public class KillActionAddon extends ActionAddon<EntityDeathEvent> implements Listener {

    @NotNull
    @Override
    public Class<EntityDeathEvent> getEventType() {
        return EntityDeathEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "kill";
    }

    @EventHandler(ignoreCancelled = true)
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getDamageSource().getCausingEntity() instanceof Player)) {
            return;
        }
        fireEvent(event, event.getEntity().getType().toString());
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

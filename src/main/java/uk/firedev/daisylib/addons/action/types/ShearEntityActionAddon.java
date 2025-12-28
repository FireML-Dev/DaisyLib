package uk.firedev.daisylib.addons.action.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class ShearEntityActionAddon extends ActionAddon<PlayerShearEntityEvent> implements Listener {

    @NotNull
    @Override
    public Class<PlayerShearEntityEvent> getEventType() {
        return PlayerShearEntityEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "shear-entity";
    }

    @EventHandler(ignoreCancelled = true)
    public void onShear(PlayerShearEntityEvent event) {
        fireEvent(event, event.getEntity().getType().toString());
    }

    @NotNull
    @Override
    public Plugin getPlugin() {
        return DaisyLibPlugin.getInstance();
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "FireML";
    }

}

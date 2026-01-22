package uk.firedev.daisylib.addons.action.types;

import io.papermc.paper.event.block.PlayerShearBlockEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class ShearBlockActionAddon extends ActionAddon<PlayerShearBlockEvent> implements Listener {

    @NonNull
    @Override
    public Class<PlayerShearBlockEvent> getEventType() {
        return PlayerShearBlockEvent.class;
    }

    @NonNull
    @Override
    public String getKey() {
        return "shear-block";
    }

    @EventHandler(ignoreCancelled = true)
    public void onShear(PlayerShearBlockEvent event) {
        fireEvent(event, event.getBlock().getType().toString());
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

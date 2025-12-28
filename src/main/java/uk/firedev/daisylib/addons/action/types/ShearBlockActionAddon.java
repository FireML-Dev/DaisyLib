package uk.firedev.daisylib.addons.action.types;

import io.papermc.paper.event.block.PlayerShearBlockEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class ShearBlockActionAddon extends ActionAddon<PlayerShearBlockEvent> implements Listener {

    @NotNull
    @Override
    public Class<PlayerShearBlockEvent> getEventType() {
        return PlayerShearBlockEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "shear-block";
    }

    @EventHandler(ignoreCancelled = true)
    public void onShear(PlayerShearBlockEvent event) {
        fireEvent(event, event.getBlock().getType().toString());
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

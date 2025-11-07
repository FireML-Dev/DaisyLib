package uk.firedev.daisylib.addons.action.types;

import io.papermc.paper.event.block.PlayerShearBlockEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;

public class ShearBlockActionAddon extends ActionAddon<PlayerShearBlockEvent> implements Listener {

    @NotNull
    @Override
    public Class<PlayerShearBlockEvent> getEventType() {
        return PlayerShearBlockEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "shear-entity";
    }

    @EventHandler(ignoreCancelled = true)
    public void onShear(PlayerShearBlockEvent event) {
        fireEvent(event, event.getBlock().getType().toString());
    }

}

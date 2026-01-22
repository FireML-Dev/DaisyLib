package uk.firedev.daisylib.addons.action.types;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

// TODO fishing plugins like EMF break this.
public class FishActionAddon extends ActionAddon<PlayerFishEvent> implements Listener {

    @NonNull
    @Override
    public String getKey() {
        return "fish";
    }

    @NonNull
    @Override
    public Class<PlayerFishEvent> getEventType() {
        return PlayerFishEvent.class;
    }

    @EventHandler(ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        if (!event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            return;
        }
        if (!(event.getCaught() instanceof Item item)) {
            return;
        }
        fireEvent(event, item.getItemStack().getType().toString());
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

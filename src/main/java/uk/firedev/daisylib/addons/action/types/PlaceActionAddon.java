package uk.firedev.daisylib.addons.action.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class PlaceActionAddon extends ActionAddon<BlockPlaceEvent> implements Listener {

    @NonNull
    @Override
    public Class<BlockPlaceEvent> getEventType() {
        return BlockPlaceEvent.class;
    }

    @NonNull
    @Override
    public String getKey() {
        return "place";
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        fireEvent(event, event.getBlockPlaced().getType().toString());
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

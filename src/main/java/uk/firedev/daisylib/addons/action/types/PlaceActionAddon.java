package uk.firedev.daisylib.addons.action.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.local.DaisyLib;

public class PlaceActionAddon extends ActionAddon<BlockPlaceEvent> implements Listener {

    @NotNull
    @Override
    public Class<BlockPlaceEvent> getEventType() {
        return BlockPlaceEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "place";
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        fireEvent(event, event.getBlockPlaced().getType().toString());
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

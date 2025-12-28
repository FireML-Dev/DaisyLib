package uk.firedev.daisylib.addons.action.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class BreakActionAddon extends ActionAddon<BlockBreakEvent> implements Listener {

    @NotNull
    @Override
    public Class<BlockBreakEvent> getEventType() {
        return BlockBreakEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "break";
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
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

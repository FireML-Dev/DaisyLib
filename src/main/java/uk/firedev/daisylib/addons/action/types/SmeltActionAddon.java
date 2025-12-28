package uk.firedev.daisylib.addons.action.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class SmeltActionAddon extends ActionAddon<FurnaceSmeltEvent> implements Listener {

    @NotNull
    @Override
    public Class<FurnaceSmeltEvent> getEventType() {
        return FurnaceSmeltEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "smelt";
    }

    @EventHandler(ignoreCancelled = true)
    public void onSmelt(FurnaceSmeltEvent event) {
        fireEvent(event, event.getSource().getType().toString());
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

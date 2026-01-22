package uk.firedev.daisylib.addons.action.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class ConsumeActionAddon extends ActionAddon<PlayerItemConsumeEvent> implements Listener {

    @NonNull
    @Override
    public Class<PlayerItemConsumeEvent> getEventType() {
        return PlayerItemConsumeEvent.class;
    }

    @NonNull
    @Override
    public String getKey() {
        return "consume";
    }

    @EventHandler(ignoreCancelled = true)
    public void onConsume(PlayerItemConsumeEvent event) {
        fireEvent(event, event.getItem().getType().toString());
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

package uk.firedev.daisylib.addons.action.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;

public class ConsumeActionAddon extends ActionAddon<PlayerItemConsumeEvent> implements Listener {

    @NotNull
    @Override
    public Class<PlayerItemConsumeEvent> getEventType() {
        return PlayerItemConsumeEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "consume";
    }

    @EventHandler(ignoreCancelled = true)
    public void onConsume(PlayerItemConsumeEvent event) {
        fireEvent(event, event.getItem().getType().toString());
    }

}

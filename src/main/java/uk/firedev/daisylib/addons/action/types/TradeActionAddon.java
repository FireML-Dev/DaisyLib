package uk.firedev.daisylib.addons.action.types;

import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class TradeActionAddon extends ActionAddon<PlayerTradeEvent> implements Listener {

    @NotNull
    @Override
    public Class<PlayerTradeEvent> getEventType() {
        return PlayerTradeEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "trade";
    }

    @EventHandler(ignoreCancelled = true)
    public void onTrade(PlayerTradeEvent event) {
        fireEvent(event, null);
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

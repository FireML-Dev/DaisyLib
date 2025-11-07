package uk.firedev.daisylib.addons.action.types;

import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;

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
        fireEvent(event, event.getTrade().getResult().getType().toString());
    }

}

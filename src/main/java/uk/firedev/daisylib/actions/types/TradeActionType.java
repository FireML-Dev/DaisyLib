package uk.firedev.daisylib.actions.types;

import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;

public class TradeActionType extends ActionType {

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "trade";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTrade(PlayerTradeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        fire(ActionContext.create()
                .withPlayer(event.getPlayer())
                .withAffectedEntity(event.getVillager())
                .withItem(event.getTrade().getResult())
        );
    }
}

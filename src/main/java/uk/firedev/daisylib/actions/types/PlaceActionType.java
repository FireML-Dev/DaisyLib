package uk.firedev.daisylib.actions.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;

public class PlaceActionType extends ActionType {

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "place";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlaced(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        fire(ActionContext.create()
                .withPlayer(event.getPlayer())
                .withBlock(event.getBlock())
        );
    }

}

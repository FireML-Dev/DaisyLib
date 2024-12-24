package uk.firedev.daisylib.actions.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;

public class BreakActionType extends ActionType {

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "break";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreakBlock(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        fire(ActionContext.create()
                .withPlayer(event.getPlayer())
                .withBlock(event.getBlock())
                .withAffectedEntity(event.getPlayer())
        );
    }

}

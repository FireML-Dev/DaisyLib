package uk.firedev.daisylib.actions.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;

public class ShearActionType extends ActionType {

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "shear";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onShear(PlayerShearEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        fire(ActionContext.create()
                .withPlayer(event.getPlayer())
                .withAffectedEntity(event.getEntity())
        );
    }

}

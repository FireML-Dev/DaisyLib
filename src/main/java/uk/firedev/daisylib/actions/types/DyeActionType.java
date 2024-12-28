package uk.firedev.daisylib.actions.types;

import io.papermc.paper.event.entity.EntityDyeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;

public class DyeActionType extends ActionType {

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "dye";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDyeEntity(EntityDyeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        fire(ActionContext.create()
                .withPlayer(event.getPlayer())
                .withAffectedEntity(event.getEntity())
        );
    }

}

package uk.firedev.daisylib.actions.types;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityBreedEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;

public class BreedActionType extends ActionType {

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "breed";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreed(EntityBreedEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getBreeder() instanceof Player player)) {
            return;
        }
        fire(ActionContext.create()
                .withPlayer(player)
                .withAffectedEntity(event.getEntity())
        );
    }
}

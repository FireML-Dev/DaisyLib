package uk.firedev.daisylib.actions.types;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;

public class KillActionType extends ActionType {

        @NotNull
        @Override
        public String getActionIdentifier() {
            return "kill";
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onEntityDeath(EntityDeathEvent event) {
            if (event.isCancelled()) {
                return;
            }
            Player killer = event.getEntity().getKiller();
            if (killer == null) {
                return;
            }
            fire(ActionContext.create()
                    .withPlayer(killer)
                    .withAffectedEntity(event.getEntity())
            );
        }

}

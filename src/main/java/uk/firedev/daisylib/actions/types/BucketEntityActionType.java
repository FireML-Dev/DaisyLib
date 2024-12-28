package uk.firedev.daisylib.actions.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;

public class BucketEntityActionType extends ActionType {

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "bucket-entity";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBucketEntity(PlayerBucketEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        fire(ActionContext.create()
                .withPlayer(event.getPlayer())
                .withAffectedEntity(event.getEntity())
        );
    }

}

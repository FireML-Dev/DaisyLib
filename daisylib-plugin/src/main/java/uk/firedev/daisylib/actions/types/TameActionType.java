package uk.firedev.daisylib.actions.types;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;
import uk.firedev.daisylib.local.DaisyLib;

public class TameActionType extends ActionType {

    @NotNull
    @Override
    public Plugin getPlugin() {
        return DaisyLib.getInstance();
    }

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "tame";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTame(EntityTameEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getOwner() instanceof Player player)) {
            return;
        }
        fire(ActionContext.create()
                .withPlayer(player)
                .withAffectedEntity(event.getEntity())
        );
    }
}

package uk.firedev.daisylib.actions.types;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;
import uk.firedev.daisylib.local.DaisyLib;


public class ExplodeActionType extends ActionType {

    @NotNull
    @Override
    public Plugin getPlugin() {
        return DaisyLib.getInstance();
    }

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "explode";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onExplodeEntity(EntityExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Entity entity = event.getEntity();
        Entity source = null;

        if (entity instanceof TNTPrimed tntPrimed) {
            source = tntPrimed.getSource();
        } else if (entity instanceof Creeper creeper) {
            source = creeper.getIgniter();
        }

        if (!(source instanceof Player player)) {
            return;
        }

        event.blockList().forEach(block -> fire(ActionContext.create()
                .withPlayer(player)
                .withBlock(block)
                .withAffectedEntity(entity)
        ));
    }

}

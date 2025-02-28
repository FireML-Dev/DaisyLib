package uk.firedev.daisylib.actions.types;

import org.bukkit.block.data.Brushable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;
import uk.firedev.daisylib.local.DaisyLib;

public class BrushActionType extends ActionType {

    @NotNull
    @Override
    public Plugin getPlugin() {
        return DaisyLib.getInstance();
    }

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "brush";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBrush(BlockDropItemEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getBlockState().getBlockData() instanceof Brushable)) {
            return;
        }
        fire(ActionContext.create()
                .withPlayer(event.getPlayer())
                .withBlock(event.getBlock())
        );
    }
}

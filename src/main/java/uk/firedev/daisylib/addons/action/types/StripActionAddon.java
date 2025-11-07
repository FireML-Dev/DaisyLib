package uk.firedev.daisylib.addons.action.types;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;

public class StripActionAddon extends ActionAddon<EntityChangeBlockEvent> implements Listener {

    @NotNull
    @Override
    public Class<EntityChangeBlockEvent> getEventType() {
        return EntityChangeBlockEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "strip";
    }

    @EventHandler(ignoreCancelled = true)
    public void onStrip(EntityChangeBlockEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Material from = event.getBlock().getType();
        Material to = event.getTo();
        if (!checkLog(from, to) || !checkBamboo(from, to)) {
            return;
        }
        System.out.println(from);
        System.out.println(to);
        fireEvent(event, from.toString());
    }

    private boolean checkLog(@NotNull Material from, @NotNull Material to) {
        if (!Tag.LOGS.isTagged(from)) {
            return false;
        }
        // Currently the only way to validate a stripped log :(
        return to.toString().startsWith("STRIPPED_");
    }

    private boolean checkBamboo(@NotNull Material from, @NotNull Material to) {
        return from.equals(Material.BAMBOO_BLOCK) && to.equals(Material.STRIPPED_BAMBOO_BLOCK);
    }

}

package uk.firedev.daisylib.addons.action.types;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;

import java.util.Map;

public class BrushActionAddon extends ActionAddon<EntityChangeBlockEvent> implements Listener {

    private static final Map<Material, Material> BRUSH_MAP = Map.of(
        Material.SUSPICIOUS_GRAVEL, Material.GRAVEL,
        Material.SUSPICIOUS_SAND, Material.SAND
    );

    @NotNull
    @Override
    public Class<EntityChangeBlockEvent> getEventType() {
        return EntityChangeBlockEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "brush";
    }

    @EventHandler(ignoreCancelled = true)
    public void onBrush(EntityChangeBlockEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Material from = event.getBlock().getType();
        Material to = event.getTo();
        if (!checkMaterial(from, to)) {
            return;
        }
        fireEvent(event, from.toString());
    }

    private boolean checkMaterial(@NotNull Material from, @NotNull Material to) {
        Material brushed = BRUSH_MAP.get(from);
        return to.equals(brushed);
    }

}

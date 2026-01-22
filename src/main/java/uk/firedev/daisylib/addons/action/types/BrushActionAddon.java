package uk.firedev.daisylib.addons.action.types;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

import java.util.Map;

public class BrushActionAddon extends ActionAddon<EntityChangeBlockEvent> implements Listener {

    private static final Map<Material, Material> BRUSH_MAP = Map.of(
        Material.SUSPICIOUS_GRAVEL, Material.GRAVEL,
        Material.SUSPICIOUS_SAND, Material.SAND
    );

    @NonNull
    @Override
    public Class<EntityChangeBlockEvent> getEventType() {
        return EntityChangeBlockEvent.class;
    }

    @NonNull
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

    private boolean checkMaterial(@NonNull Material from, @NonNull Material to) {
        Material brushed = BRUSH_MAP.get(from);
        return to.equals(brushed);
    }

    @NonNull
    @Override
    public Plugin getPlugin() {
        return DaisyLibPlugin.getInstance();
    }

    @NonNull
    @Override
    public String getAuthor() {
        return "FireML";
    }

}

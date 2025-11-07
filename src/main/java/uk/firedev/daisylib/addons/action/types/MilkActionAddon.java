package uk.firedev.daisylib.addons.action.types;

import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.List;

public class MilkActionAddon extends ActionAddon<PlayerInteractEntityEvent> implements Listener {

    private static final List<EntityType> MILKABLE = List.of(
        EntityType.COW,
        EntityType.MOOSHROOM,
        EntityType.GOAT
    );

    @NotNull
    @Override
    public Class<PlayerInteractEntityEvent> getEventType() {
        return PlayerInteractEntityEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "milk";
    }

    @EventHandler(ignoreCancelled = true)
    public void onMilk(PlayerInteractEntityEvent event) {
        if (!holdingBucket(event.getPlayer().getInventory(), event.getHand())) {
            return;
        }
        if (!MILKABLE.contains(event.getRightClicked().getType())) {
            return;
        }
        fireEvent(event, event.getRightClicked().getType().toString());
    }

    private boolean holdingBucket(@NotNull PlayerInventory inv, @NotNull EquipmentSlot hand) {
        return switch (hand) {
            case HAND -> inv.getItemInMainHand().getType().equals(Material.BUCKET);
            case OFF_HAND -> inv.getItemInOffHand().getType().equals(Material.BUCKET);
            // No.
            default -> false;
        };
    }

    @NotNull
    @Override
    public Plugin getPlugin() {
        return DaisyLib.getInstance();
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "FireML";
    }

}

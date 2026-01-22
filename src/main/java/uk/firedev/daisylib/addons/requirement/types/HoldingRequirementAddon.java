package uk.firedev.daisylib.addons.requirement.types;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementData;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;
import uk.firedev.daisylib.util.Utils;

import java.util.List;

public class HoldingRequirementAddon extends RequirementAddon {

    @Override
    public boolean checkRequirement(@NonNull RequirementData data, @NonNull List<String> values) {
        Player player = data.getPlayer();
        if (player == null) {
            return false;
        }
        for (String value : values) {
            ItemType type = Utils.getItemType(value);
            if (type == null) {
                Loggers.warn(getClass(), value + " is not a valid ItemType");
                continue;
            }
            if (eitherHandHasItemType(player, type)) {
                return true;
            }
        }
        return false;
    }

    private boolean eitherHandHasItemType(@NonNull Player player, @NonNull ItemType type) {
        PlayerInventory inventory = player.getInventory();
        ItemStack handItem = inventory.getItemInMainHand();
        ItemStack offHandItem = inventory.getItemInOffHand();
        return type.equals(handItem.getType().asItemType()) || type.equals(offHandItem.getType().asItemType());
    }

    @Override
    public @NonNull String getKey() {
        return "Holding";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull Plugin getPlugin() {
        return DaisyLibPlugin.getInstance();
    }

}

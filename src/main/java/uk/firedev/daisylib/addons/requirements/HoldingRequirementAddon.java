package uk.firedev.daisylib.addons.requirements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.utils.ItemUtils;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.addons.requirement.RequirementData;
import java.util.List;

public class HoldingRequirementAddon extends RequirementAddon {

    @Override
    public boolean checkRequirement(@NotNull RequirementData data, @NotNull List<String> values) {
        Player player = data.getPlayer();
        if (player == null) {
            return false;
        }
        for (String value : values) {
            ItemType type = ItemUtils.getItemType(value);
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

    private boolean eitherHandHasItemType(@NotNull Player player, @NotNull ItemType type) {
        PlayerInventory inventory = player.getInventory();
        ItemStack handItem = inventory.getItemInMainHand();
        ItemStack offHandItem = inventory.getItemInOffHand();
        return type.equals(handItem.getType().asItemType()) || type.equals(offHandItem.getType().asItemType());
    }

    @Override
    public @NotNull String getIdentifier() {
        return "Holding";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull Plugin getOwningPlugin() {
        return DaisyLib.getInstance();
    }

}

package uk.firedev.daisylib.common.addons.requirement.defaults;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.common.DaisyLib;
import uk.firedev.daisylib.common.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.common.addons.requirement.RequirementData;
import uk.firedev.daisylib.common.utils.CommonUtils;

import java.util.List;

public class HoldingRequirementAddon extends RequirementAddon {

    @Override
    public boolean check(@NonNull RequirementData data, @NonNull List<String> values) {
        Player player = data.player();
        if (player == null) {
            return false;
        }
        for (String value : values) {
            Material material = CommonUtils.getMaterial(value);
            if (material == null) {
                getLogging().warn(value + " is not a valid item");
                continue;
            }
            if (eitherHandHasItemType(player, material)) {
                return true;
            }
        }
        return false;
    }

    private boolean eitherHandHasItemType(@NonNull Player player, @NonNull Material material) {
        PlayerInventory inventory = player.getInventory();
        ItemStack handItem = inventory.getItemInMainHand();
        ItemStack offHandItem = inventory.getItemInOffHand();
        return material.equals(handItem.getType()) || material.equals(offHandItem.getType());
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
        return DaisyLib.get().getPlugin();
    }

}

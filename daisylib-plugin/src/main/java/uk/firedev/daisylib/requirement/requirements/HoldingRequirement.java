package uk.firedev.daisylib.requirement.requirements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.requirement.RequirementData;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.api.utils.ItemUtils;

import java.util.List;

public class HoldingRequirement implements RequirementType {

    @Override
    public boolean checkRequirement(@NotNull RequirementData data, @NotNull List<String> values) {
        Player player = data.getPlayer();
        if (player == null) {
            return false;
        }
        for (String value : values) {
            Material material = ItemUtils.getMaterial(value);
            if (material == null) {
                Loggers.warn(getComponentLogger(), value + " is not a valid material");
                continue;
            }
            if (eitherHandHasMaterial(player, material)) {
                return true;
            }
        }
        return false;
    }

    private boolean eitherHandHasMaterial(@NotNull Player player, @NotNull Material material) {
        PlayerInventory inventory = player.getInventory();
        return inventory.getItemInMainHand().getType().equals(material) || inventory.getItemInOffHand().getType().equals(material);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "HOLDING";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return DaisyLib.getInstance();
    }

}

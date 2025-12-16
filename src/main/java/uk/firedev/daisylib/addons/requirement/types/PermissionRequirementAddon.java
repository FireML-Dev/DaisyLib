package uk.firedev.daisylib.addons.requirement.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementData;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.List;

public class PermissionRequirementAddon extends RequirementAddon {

    @Override
    public boolean checkRequirement(@NotNull RequirementData data, @NotNull List<String> values) {
        Player player = data.getPlayer();
        if (player == null) {
            return false;
        }
        for (String value : values) {
            if (player.hasPermission(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getKey() {
        return "Permission";
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

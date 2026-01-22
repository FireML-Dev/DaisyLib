package uk.firedev.daisylib.addons.requirement.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementData;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

import java.util.List;

public class PermissionRequirementAddon extends RequirementAddon {

    @Override
    public boolean checkRequirement(@NonNull RequirementData data, @NonNull List<String> values) {
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
    public @NonNull String getKey() {
        return "Permission";
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

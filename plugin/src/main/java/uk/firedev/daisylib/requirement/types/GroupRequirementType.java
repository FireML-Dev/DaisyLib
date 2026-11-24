package uk.firedev.daisylib.requirement.types;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.external.luckperms.LuckPermsWrapper;
import uk.firedev.daisylib.external.vault.VaultWrapper;
import uk.firedev.daisylib.requirement.RequirementContext;
import uk.firedev.daisylib.requirement.RequirementType;

import java.util.List;
import java.util.UUID;

public class GroupRequirementType extends RequirementType {

    @Override
    public boolean check(@NonNull RequirementContext context, @NonNull List<String> values) {
        UUID player = context.rawPlayer();
        if (player == null) {
            return false;
        }
        boolean passed = LuckPermsWrapper.isInGroups(player, values);
        debugLogStatus(passed);
        return passed;
    }

    @Override
    public @NonNull String getKey() {
        return "Group";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull Plugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return Bukkit.getPluginManager().isPluginEnabled("LuckPerms");
    }

}

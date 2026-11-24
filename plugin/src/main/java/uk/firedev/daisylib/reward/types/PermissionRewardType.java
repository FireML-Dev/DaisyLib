package uk.firedev.daisylib.reward.types;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.external.vault.VaultWrapper;
import uk.firedev.daisylib.reward.RewardType;

public class PermissionRewardType extends RewardType {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        Permission permission = VaultWrapper.get().getPermissionOrNull();
        if (permission != null) {
            permission.playerAdd(player.getPlayer(), value);
        }
    }

    @Override
    public @NonNull String getIdentifier() {
        return "Permission";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull JavaPlugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return Bukkit.getPluginManager().isPluginEnabled("Vault");
    }
}

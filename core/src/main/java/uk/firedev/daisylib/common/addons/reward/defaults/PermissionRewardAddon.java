package uk.firedev.daisylib.common.addons.reward.defaults;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.DaisyLib;
import uk.firedev.daisylib.common.addons.reward.RewardAddon;
import uk.firedev.daisylib.common.external.vault.VaultWrapper;

public class PermissionRewardAddon extends RewardAddon {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        Permission permission = VaultWrapper.get().getPermissionOrNull();
        if (permission == null) {
            getLogging().warn("Vault Permission not found. Cannot give " + getKey() + " reward.");
            return;
        }
        permission.playerAdd(null, player, value);
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
        return DaisyLib.get().getPlugin();
    }

}

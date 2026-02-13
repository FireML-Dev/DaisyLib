package uk.firedev.daisylib.addons.reward.types;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;
import uk.firedev.daisylib.util.VaultManager;

public class PermissionRewardAddon extends RewardAddon {

    @Override
    public void doReward(@NonNull Player player, @NonNull String value) {
        Permission permission = VaultManager.getInstance().getPermissions();
        if (permission == null) {
            Loggers.warn(getClass(), "DaisyLib's VaultManager is not enabled! Enable to use this RewardAddon.");
        } else {
            permission.playerAdd(null, player, value);
        }
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

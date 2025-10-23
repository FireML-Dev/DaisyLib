package uk.firedev.daisylib.addons.reward.types;

import net.milkbowl.vault2.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.addons.reward.RewardAddon;

public class PermissionRewardAddon extends RewardAddon {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        Permission permission = VaultManager.getInstance().getPermissions();
        if (permission == null) {
            Loggers.warn(getClass(), "DaisyLib's VaultManager is not enabled! Enable to use this RewardAddon.");
        } else {
            permission.playerAdd(null, player, value);
        }
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

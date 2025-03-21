package uk.firedev.daisylib.addons.rewards;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.api.addons.reward.RewardAddon;

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
    public @NotNull String getIdentifier() {
        return "Permission";
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

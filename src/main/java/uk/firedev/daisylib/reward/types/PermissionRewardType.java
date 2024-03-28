package uk.firedev.daisylib.reward.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.VaultManager;
import uk.firedev.daisylib.reward.RewardType;

public class PermissionRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value) {
        if (VaultManager.getPermissions() == null) {
            Loggers.warning(getPlugin().getLogger(), "DaisyLib's VaultManager is not enabled! Enable to use RewardType " + getIdentifier());
        } else {
            VaultManager.getPermissions().playerAdd(null, player, value);
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "PERMISSION";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull JavaPlugin getPlugin() {
        return DaisyLib.getInstance();
    }

}

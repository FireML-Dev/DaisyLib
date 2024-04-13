package uk.firedev.daisylib.reward.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.ObjectUtils;

public class MoneyRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value) {
        if (!ObjectUtils.isDouble(value)) {
            Loggers.warning(getPlugin().getLogger(), "Invalid number specified for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        double amount = Double.parseDouble(value);
        if (amount < 0) {
            amount = 0.0D;
        }
        if (VaultManager.getEconomy() == null) {
            Loggers.warning(getPlugin().getLogger(), "DaisyLib's VaultManager is not enabled! Enable to use RewardType " + getIdentifier());
        } else {
            VaultManager.getEconomy().depositPlayer(player, amount);
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "MONEY";
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

package uk.firedev.daisylib.reward.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.VaultManager;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.ObjectUtils;

import java.util.logging.Level;

public class MoneyRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value, int quantity) {
        if (!ObjectUtils.isDouble(value)) {
            Loggers.log(Level.INFO, getPlugin().getLogger(), "Invalid number specified for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        double amount = Double.parseDouble(value);
        if (amount < 0) {
            amount = 0.0D;
        }
        VaultManager.getEconomy().depositPlayer(player, amount);
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

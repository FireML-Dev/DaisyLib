package uk.firedev.daisylib.addons.reward.types;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;
import uk.firedev.daisylib.util.Utils;
import uk.firedev.daisylib.util.VaultManager;

import java.math.BigDecimal;

public class MoneyRewardAddon extends RewardAddon {

    @Override
    public void doReward(@NonNull Player player, @NonNull String value) {
        Economy economy = VaultManager.getInstance().getEconomy();
        if (economy == null) {
            Loggers.warn(getClass(), "Vault Economy not found! Enable to use this RewardAddon.");
            return;
        }
        Double amount = Utils.getDouble(value);
        if (amount == null) {
            Loggers.warn(getClass(), "Invalid number specified: " + value);
            return;
        }
        if (amount < 0) {
            amount = 0.0D;
        }
        economy.depositPlayer(player, amount);
    }

    @Override
    public @NonNull String getKey() {
        return "Money";
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

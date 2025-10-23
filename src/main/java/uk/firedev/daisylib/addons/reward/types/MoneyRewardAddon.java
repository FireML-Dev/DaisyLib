package uk.firedev.daisylib.addons.reward.types;

import net.milkbowl.vault2.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.utils.ObjectUtils;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.addons.reward.RewardAddon;

import java.math.BigDecimal;

public class MoneyRewardAddon extends RewardAddon {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        Economy economy = VaultManager.getInstance().getEconomy();
        if (economy == null) {
            Loggers.warn(getClass(), "Vault Economy not found! Enable to use this RewardAddon.");
            return;
        }
        Double amount = ObjectUtils.getDouble(value);
        if (amount == null) {
            Loggers.warn(getClass(), "Invalid number specified: " + value);
            return;
        }
        if (amount < 0) {
            amount = 0.0D;
        }
        economy.deposit(getPlugin().getName(), player.getUniqueId(), BigDecimal.valueOf(amount));
    }

    @Override
    public @NotNull String getKey() {
        return "Money";
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

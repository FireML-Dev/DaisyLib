package uk.firedev.daisylib.reward.types;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.external.vault.VaultWrapper;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.CommonUtils;

public class MoneyRewardType extends RewardType {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        Double amount = CommonUtils.getDouble(value);
        if (amount == null || amount < 0) {
            DaisyLib.get().getLogging().warn("Invalid number for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        Economy economy = VaultWrapper.get().getEconomyOrNull();
        if (economy == null) {
            return;
        }
        economy.depositPlayer(player, amount);
    }

    @Override
    public @NonNull String getIdentifier() {
        return "Money";
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

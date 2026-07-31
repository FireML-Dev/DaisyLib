package uk.firedev.daisylib.common.addons.reward.defaults;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.DaisyLib;
import uk.firedev.daisylib.common.addons.reward.RewardAddon;
import uk.firedev.daisylib.common.external.vault.VaultWrapper;
import uk.firedev.daisylib.common.utils.CommonUtils;

public class MoneyRewardAddon extends RewardAddon {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        Economy economy = VaultWrapper.get().getEconomyOrNull();
        if (economy == null) {
            getLogging().warn("Vault Economy not found. Cannot give " + getKey() + " reward.");
            return;
        }
        Double amount = CommonUtils.getDouble(value);
        if (amount == null) {
            getLogging().warn("Invalid number specified: " + value);
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
        return DaisyLib.get().getPlugin();
    }

}

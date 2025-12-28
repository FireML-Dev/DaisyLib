package uk.firedev.daisylib.addons.reward.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;
import uk.firedev.daisylib.util.Utils;


public class ExpRewardAddon extends RewardAddon {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        Integer amount = Utils.getInt(value);
        if (amount == null) {
            Loggers.info(getClass(), "Invalid number specified: " + value);
            return;
        }
        player.giveExp(amount);
    }

    @Override
    public @NotNull String getKey() {
        return "Exp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return DaisyLibPlugin.getInstance();
    }

}

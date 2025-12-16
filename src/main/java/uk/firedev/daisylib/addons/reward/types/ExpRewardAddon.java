package uk.firedev.daisylib.addons.reward.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.utils.ObjectUtils;

public class ExpRewardAddon extends RewardAddon {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        Integer amount = ObjectUtils.getInt(value);
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
        return DaisyLib.getInstance();
    }

}

package uk.firedev.daisylib.reward.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.utils.ObjectUtils;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardType;

public class ExpRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        Integer amount = ObjectUtils.getInt(value);
        if (amount == null) {
            Loggers.info(getComponentLogger(), "Invalid number specified for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        player.giveExp(amount);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "EXP";
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

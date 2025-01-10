package uk.firedev.daisylib.reward.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.api.utils.ObjectUtils;

public class ExpRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        
        if (!ObjectUtils.isDouble(value)) {
            Loggers.info(getComponentLogger(), "Invalid number specified for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        int amount = (int) Double.parseDouble(value);
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

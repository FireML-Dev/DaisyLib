package uk.firedev.daisylib.addons.reward.defaults;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.utils.CommonUtils;

public class ExpRewardAddon extends RewardAddon {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        Integer amount = CommonUtils.getInt(value);
        if (amount == null) {
            getLogging().info("Invalid number specified: " + value);
            return;
        }
        player.giveExp(amount);
    }

    @Override
    public @NonNull String getKey() {
        return "Exp";
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

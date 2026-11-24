package uk.firedev.daisylib.reward.types;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.reward.RewardType;

public class HungerRewardType extends RewardType {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        int rewardHunger;
        try {
            rewardHunger = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            DaisyLib.get().getLogging().warn("Invalid number specified for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        player.setFoodLevel(player.getFoodLevel() + rewardHunger);
    }

    @Override
    public @NonNull String getIdentifier() {
        return "Hunger";
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
        return true;
    }
}

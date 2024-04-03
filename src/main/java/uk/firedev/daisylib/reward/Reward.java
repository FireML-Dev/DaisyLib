package uk.firedev.daisylib.reward;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;

import java.util.logging.Level;

public class Reward {

    private @NotNull String key;
    private @NotNull String value;
    private final JavaPlugin plugin;

    public Reward(@NotNull String identifier, @NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        String[] split = identifier.split(":");
        try {
            this.key = split[0];
        } catch (IndexOutOfBoundsException ex) {
            Loggers.log(Level.INFO, this.plugin.getLogger(), "Broken reward " + identifier);
            this.key = "";
        }
        try {
            this.value = split[1];
        } catch (IndexOutOfBoundsException ex) {
            this.value = "";
        }
    }

    public void rewardPlayer(@NotNull Player player) {
        for (RewardType rewardType : RewardManager.getInstance().getRegisteredRewardTypes()) {
            if (rewardType.isApplicable(this.key)) {
                rewardType.doReward(player, this.key, this.value);
                return;
            }
        }
    }

}

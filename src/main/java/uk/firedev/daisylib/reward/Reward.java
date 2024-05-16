package uk.firedev.daisylib.reward;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reward {

    private @NotNull String fullIdentifier;
    private @NotNull String key;
    private @NotNull String value;
    private final JavaPlugin plugin;

    public Reward(@NotNull String identifier, @NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.fullIdentifier = identifier;
        String[] split = identifier.split(":");
        try {
            this.key = split[0];
            this.value = String.join(":", Arrays.copyOfRange(split, 1, split.length));
        } catch (IndexOutOfBoundsException ex) {
            Loggers.warn(getComponentLogger(), "Broken reward " + identifier);
            this.key = "";
            this.value = "";
        }
    }

    public void rewardPlayer(@NotNull Player player) {
        if (this.key.isEmpty() || this.value.isEmpty()) {
            Loggers.warn(getComponentLogger(), "Attempted to give an invalid Reward. Please check for earlier warnings.");
            return;
        }
        for (RewardType rewardType : RewardManager.getInstance().getRegisteredRewardTypes()) {
            if (rewardType.isApplicable(this.key)) {
                rewardType.doReward(player, this.key, this.value);
                return;
            }
        }
        Loggers.warn(getComponentLogger(), "Invalid reward. Possible typo?: " + fullIdentifier);
    }

    public ComponentLogger getComponentLogger() {
        if (plugin instanceof DaisyLib) {
            return plugin.getComponentLogger();
        }
        return ComponentLogger.logger("DaisyLib via " + plugin.getName());
    }

}

package uk.firedev.daisylib.reward;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.logging.Logger;

public interface RewardType {

    default boolean isApplicable(@NotNull String key) {
        return key.equalsIgnoreCase(getIdentifier());
    }

    void doReward(@NotNull Player player, @NotNull String key, @NotNull String value);

    @NotNull String getIdentifier();

    @NotNull String getAuthor();

    @NotNull JavaPlugin getPlugin();

    default boolean register() {
        return RewardManager.getInstance().registerRewardType(this);
    }

    default ComponentLogger getComponentLogger() {
        if (getPlugin() instanceof DaisyLib) {
            return getPlugin().getComponentLogger();
        }
        return ComponentLogger.logger("DaisyLib via " + getPlugin().getName());
    }

    default Logger getLogger() {
        if (getPlugin() instanceof DaisyLib) {
            return getPlugin().getLogger();
        }
        return Logger.getLogger("DaisyLib via " + getPlugin().getName());
    }

    default boolean checkAsync() {
        if (!Bukkit.isPrimaryThread()) {
            IllegalAccessException ex = new IllegalAccessException("Attempted to trigger a RewardType asynchronously. This is not supported!");
            Loggers.logException(getComponentLogger(), ex);
            return false;
        }
        return true;
    }

}
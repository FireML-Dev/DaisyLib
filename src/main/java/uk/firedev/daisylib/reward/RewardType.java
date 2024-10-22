package uk.firedev.daisylib.reward;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.logging.Logger;

public interface RewardType {

    void doReward(@NotNull Player player, @NotNull String value);

    @NotNull String getIdentifier();

    @NotNull String getAuthor();

    @NotNull Plugin getPlugin();

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

}
package uk.firedev.daisylib.requirement;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.logging.Logger;

/**
 * A way to check if a player meets a certain requirement.
 * This interface can be implemented by third party plugins to register their own Requirement.
 */
public interface RequirementType {

    /**
     * Checks if a player meets this requirement.
     * @param player The player to check
     * @param value The value to check
     */
    boolean checkRequirement(@NotNull Player player, @NotNull String value);

    /**
     * The identifier for this Requirement
     * @return The identifier for this Requirement
     */
    @NotNull String getIdentifier();

    @NotNull String getAuthor();

    @NotNull Plugin getPlugin();

    default boolean register() {
        return RequirementManager.getInstance().registerRequirement(this);
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

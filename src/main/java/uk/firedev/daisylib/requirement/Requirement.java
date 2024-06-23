package uk.firedev.daisylib.requirement;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardManager;
import uk.firedev.daisylib.reward.RewardType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Requirement {

    private final Map<String, String> checkMap;
    private final JavaPlugin plugin;

    public Requirement(@NotNull String identifier, @NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        checkMap = new HashMap<>();
        processIdentifier(identifier);
    }

    public Requirement(@NotNull List<String> identifiers, @NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        checkMap = new HashMap<>();
        identifiers.forEach(this::processIdentifier);
    }

    private void processIdentifier(@NotNull String identifier) {
        String[] split = identifier.split(":");
        try {
            this.checkMap.putIfAbsent(split[0], String.join(":", Arrays.copyOfRange(split, 1, split.length)));
        } catch (ArrayIndexOutOfBoundsException ex) {
            Loggers.warn(getComponentLogger(), "Broken requirement " + identifier);
        }
    }

    public boolean meetsRequirements(@NotNull Player player) {
        for (Map.Entry<String, String> entry : checkMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.isEmpty() || value.isEmpty()) {
                Loggers.warn(getComponentLogger(), "Attempted to process an invalid Requirement. Please check for earlier warnings.");
                continue;
            }
            RequirementType requirementType = RequirementManager.getInstance().getRegisteredRequirements().get(key);
            if (requirementType == null) {
                Loggers.warn(getComponentLogger(), "Invalid requirement. Possible typo?: " + key + ":" + value);
                continue;
            }
            if (!requirementType.checkRequirement(player, value)) {
                return false;
            }
        }
        return true;
    }

    public ComponentLogger getComponentLogger() {
        if (plugin instanceof DaisyLib) {
            return plugin.getComponentLogger();
        }
        return ComponentLogger.logger("DaisyLib via " + plugin.getName());
    }

}

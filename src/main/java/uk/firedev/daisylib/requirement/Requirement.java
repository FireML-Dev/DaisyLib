package uk.firedev.daisylib.requirement;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Requirement {

    private final Map<String, List<String>> checkMap;
    private final Plugin plugin;

    public Requirement(@NotNull Plugin plugin) {
        checkMap = new HashMap<>();
        this.plugin = plugin;
    }

    public Requirement(@NotNull String identifier, @NotNull List<String> values, @NotNull Plugin plugin) {
        this.plugin = plugin;
        checkMap = new HashMap<>();
        processRequirement(identifier, values);
    }

    public Requirement(@NotNull Map<String, List<String>> requirements, @NotNull Plugin plugin) {
        this.plugin = plugin;
        checkMap = new HashMap<>();
        requirements.forEach(this::processRequirement);
    }

    public Requirement add(@NotNull String identifier, @NotNull List<String> values) {
        processRequirement(identifier, values);
        return this;
    }

    public Requirement add(@NotNull Map<String, List<String>> requirements) {
        requirements.forEach(this::processRequirement);
        return this;
    }

    private void processRequirement(@NotNull String identifier, @NotNull List<String> values) {
        this.checkMap.put(identifier, values);
    }

    public boolean meetsRequirements(@NotNull RequirementData data) {
        for (Map.Entry<String, List<String>> entry : checkMap.entrySet()) {
            String key = entry.getKey().toUpperCase();
            List<String> value = entry.getValue();
            if (key.isEmpty() || value.isEmpty()) {
                plugin.getLogger().warning("Attempted to process an invalid Requirement. Please check for earlier warnings.");
                continue;
            }
            RequirementType requirementType = RequirementManager.getInstance().getRegisteredRequirements().get(key);
            if (requirementType == null) {
                Loggers.warn(getComponentLogger(), "Invalid requirement. Possible typo?: " + key);
                continue;
            }
            if (!requirementType.checkRequirement(data, value)) {
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

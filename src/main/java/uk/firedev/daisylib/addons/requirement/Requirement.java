package uk.firedev.daisylib.addons.requirement;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Requirement {

    private final ComponentLogger logger = ComponentLogger.logger(getClass());
    private final Map<String, List<String>> checkMap = new HashMap<>();
    private final Plugin plugin;

    public Requirement(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    public Requirement(@NotNull String identifier, @NotNull List<String> values, @NotNull Plugin plugin) {
        this(plugin);
        add(identifier, values);
    }

    public Requirement(@NotNull Map<String, List<String>> requirements, @NotNull Plugin plugin) {
        this(plugin);
        add(requirements);
    }

    public Requirement(@Nullable ConfigurationSection section, @NotNull Plugin plugin) {
        this(plugin);
        add(section);
    }

    public Requirement add(@NotNull String identifier, @NotNull List<String> values) {
        processRequirement(identifier, values);
        return this;
    }

    public Requirement add(@NotNull Map<String, List<String>> requirements) {
        requirements.forEach(this::processRequirement);
        return this;
    }

    public Requirement add(@Nullable ConfigurationSection section) {
        if (section == null) {
            return this;
        }
        section.getKeys(false).forEach(requirementString -> {
            List<String> values = new ArrayList<>();
            if (section.isList(requirementString)) {
                values.addAll(section.getStringList(requirementString));
            } else {
                values.add(section.getString(requirementString));
            }
            processRequirement(requirementString, values);
        });
        return this;
    }

    private void processRequirement(@NotNull String identifier, @NotNull List<String> values) {
        this.checkMap.put(identifier, values);
    }

    public boolean meetsRequirements(@NotNull RequirementData data) {
        if (checkMap.isEmpty()) {
            return true;
        }
        for (Map.Entry<String, List<String>> entry : checkMap.entrySet()) {
            String key = entry.getKey().toUpperCase();
            List<String> value = entry.getValue();
            if (key.isEmpty() || value.isEmpty()) {
                plugin.getLogger().warning("Attempted to process an invalid Requirement. Please check for earlier warnings.");
                continue;
            }
            RequirementAddon requirementAddon = RequirementAddon.get(key);
            if (requirementAddon == null) {
                Loggers.warn(getComponentLogger(), "Invalid requirement. Possible typo?: " + key);
                continue;
            }
            if (!requirementAddon.checkRequirement(data, value)) {
                return false;
            }
        }
        return true;
    }

    public ComponentLogger getComponentLogger() {
        return logger;
    }

}

package uk.firedev.daisylib.addons.requirement;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.util.Loggers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Requirement {

    private final ComponentLogger logger = ComponentLogger.logger(getClass());
    private final Map<String, List<String>> checkMap = new HashMap<>();
    private final Plugin plugin;

    public Requirement(@NonNull Plugin plugin) {
        this.plugin = plugin;
    }

    public Requirement(@NonNull String identifier, @NonNull List<String> values, @NonNull Plugin plugin) {
        this(plugin);
        add(identifier, values);
    }

    public Requirement(@NonNull Map<String, List<String>> requirements, @NonNull Plugin plugin) {
        this(plugin);
        add(requirements);
    }

    public Requirement(@Nullable ConfigurationSection section, @NonNull Plugin plugin) {
        this(plugin);
        add(section);
    }

    public @NonNull Requirement add(@NonNull String identifier, @NonNull List<String> values) {
        processRequirement(identifier, values);
        return this;
    }

    public @NonNull Requirement add(@NonNull Map<String, List<String>> requirements) {
        requirements.forEach(this::processRequirement);
        return this;
    }

    public @NonNull Requirement add(@Nullable ConfigurationSection section) {
        if (section == null) {
            return this;
        }
        section.getKeys(false).forEach(requirementString -> {
            if (section.isList(requirementString)) {
                processRequirement(requirementString, section.getStringList(requirementString));
            } else {
                String value = section.getString(requirementString);
                if (value == null) {
                    return;
                }
                processRequirement(requirementString, List.of(value));
            }
        });
        return this;
    }

    private void processRequirement(@NonNull String identifier, @NonNull List<String> values) {
        this.checkMap.put(identifier, values);
    }

    public boolean meetsRequirements(@NonNull RequirementData data) {
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
            RequirementAddon requirementAddon = RequirementAddonRegistry.get().get(key);
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

    public @NonNull ComponentLogger getComponentLogger() {
        return logger;
    }

}

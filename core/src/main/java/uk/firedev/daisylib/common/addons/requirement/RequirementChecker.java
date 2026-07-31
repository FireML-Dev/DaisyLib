package uk.firedev.daisylib.common.addons.requirement;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.DaisyLib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequirementChecker {

    private final Map<String, List<String>> checkMap = new HashMap<>();

    public RequirementChecker() {}

    public RequirementChecker(@NonNull String identifier, @NonNull List<String> values) {
        add(identifier, values);
    }

    public RequirementChecker(@NonNull Map<String, List<String>> requirements) {
        add(requirements);
    }

    public RequirementChecker(@Nullable ConfigurationSection section) {
        add(section);
    }

    public RequirementChecker add(@NonNull String identifier, @NonNull List<String> values) {
        processRequirement(identifier, values);
        return this;
    }

    public RequirementChecker add(@NonNull Map<String, List<String>> requirements) {
        requirements.forEach(this::processRequirement);
        return this;
    }

    public RequirementChecker add(@Nullable ConfigurationSection section) {
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

    public boolean check(@NonNull RequirementData data) {
        for (Map.Entry<String, List<String>> entry : checkMap.entrySet()) {
            String key = entry.getKey().toUpperCase();
            List<String> value = entry.getValue();
            if (key.isEmpty() || value.isEmpty()) {
                DaisyLib.get().getLogging().warn("Attempted to process an invalid Requirement. Please check for earlier warnings.");
                continue;
            }
            RequirementAddon requirementType = RequirementAddonRegistry.get().get(key);
            if (requirementType == null) {
                DaisyLib.get().getLogging().warn("Invalid requirement. Possible typo?: " + key);
                continue;
            }
            if (!requirementType.check(data, value)) {
                return false;
            }
        }
        return true;
    }

}

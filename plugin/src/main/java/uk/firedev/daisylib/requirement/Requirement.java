package uk.firedev.daisylib.requirement;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.registry.Registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public class Requirement {

    private final Map<String, List<String>> checks = new HashMap<>();

    private Requirement() {}

    public static Requirement requirement() {
        return new Requirement();
    }

    public Requirement add(String identifier, List<String> values) {
        processRequirement(identifier, values);
        return this;
    }

    public Requirement add(Map<String, List<String>> requirements) {
        requirements.forEach(this::processRequirement);
        return this;
    }

    public Requirement add(@Nullable ConfigurationSection section) {
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

    private void processRequirement(String identifier, List<String> values) {
        if (identifier.isBlank()) {
            DaisyLib.get().getLogging().debug("Requirement identifier is blank.", new Throwable());
            return;
        }
        if (values.isEmpty()) {
            DaisyLib.get().getLogging().debug("Requirement values were not provided.", new Throwable());
            return;
        }
        this.checks.put(identifier, values);
    }

    public boolean check(Player player) {
        return check(RequirementContext.ofPlayer(player));
    }

    public boolean check(RequirementContext context) {
        for (Map.Entry<String, List<String>> entry : checks.entrySet()) {
            String key = entry.getKey().toUpperCase();
            List<String> value = entry.getValue();
            RequirementType requirementType = Registry.REQUIREMENT_TYPE.get(key);
            if (requirementType == null) {
                DaisyLib.get().getLogging().warn(key + " is not a valid RequirementType.");
                continue;
            }
            if (!requirementType.check(context, value)) {
                return false;
            }
        }
        return true;
    }

}

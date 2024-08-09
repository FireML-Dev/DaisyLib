package uk.firedev.daisylib.requirement;

import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.requirement.requirements.*;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.reward.types.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequirementManager {

    private static RequirementManager instance;

    private final Map<String, RequirementType> requirements = new HashMap<>();
    private boolean loaded = false;

    private RequirementManager() {}

    public static RequirementManager getInstance() {
        if (instance == null) {
            instance = new RequirementManager();
        }
        return instance;
    }

    public void load() {
        if (!isLoaded()) {
            Loggers.info(DaisyLib.getInstance().getComponentLogger(), "Loading RequirementManager!");
            new EXPRequirement().register();
            new HealthRequirement().register();
            new HoldingRequirement().register();
            new MoneyRequirement().register();
            new PermissionRequirement().register();
            loaded = true;
        }
    }

    public boolean isLoaded() { return loaded; }

    /**
     * Register a custom requirement.
     * @param requirementType The requirement type instance you wish to register
     * @return Whether the requirement type was added or not
     */
    public boolean registerRequirement(RequirementType requirementType) {
        String identifier = requirementType.getIdentifier().toUpperCase();
        if (requirements.containsKey(identifier)) {
            return false;
        }
        Loggers.info(DaisyLib.getInstance().getComponentLogger(),
                "<green>Registered <gold>" + identifier + "</gold> Requirement by <gold>" + requirementType.getAuthor() + "</gold> from the plugin <aqua>" + requirementType.getPlugin().getName()
        );
        requirements.put(identifier, requirementType);
        return true;
    }

    /**
     * Unregister a custom requirement.
     * @param requirementName The requirement you wish to unregister
     * @return Whether the requirement type was removed or not
     */
    public boolean unregisterRequirement(@NotNull String requirementName) {
        if (!requirements.containsKey(requirementName)) {
            return false;
        }
        requirements.remove(requirementName);
        return true;
    }

    public Map<String, RequirementType> getRegisteredRequirements() { return new HashMap<>(requirements); }

    public List<RequirementType> getRegisteredRequirementTypes() {
        return new ArrayList<>(requirements.values());
    }

}

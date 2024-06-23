package uk.firedev.daisylib.requirement;

import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.requirement.requirements.*;
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
     * @param rewardType The reward type instance you wish to register
     * @return Whether the reward type was added or not
     */
    public boolean registerRequirement(RequirementType requirement) {
        String identifier = requirement.getIdentifier().toUpperCase();
        if (requirements.containsKey(identifier)) {
            return false;
        }
        Loggers.info(DaisyLib.getInstance().getComponentLogger(),
                "<green>Registered <gold>" + identifier + "</gold> Requirement by <gold>" + requirement.getAuthor() + "</gold> from the plugin <aqua>" + requirement.getPlugin().getName()
        );
        requirements.put(identifier, requirement);
        return true;
    }

    public Map<String, RequirementType> getRegisteredRequirements() { return new HashMap<>(requirements); }

}

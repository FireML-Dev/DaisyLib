package uk.firedev.daisylib.requirement;

import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
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
            new ItemRewardType().register();
            new CommandRewardType().register();
            new PermissionRewardType().register();
            new ExpRewardType().register();
            new HealthRewardType().register();
            new MoneyRewardType().register();
            new TeleportRewardType().register();
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

    public List<RequirementType> getRegisteredRequirements() {
        return new ArrayList<>(requirements.values());
    }

}

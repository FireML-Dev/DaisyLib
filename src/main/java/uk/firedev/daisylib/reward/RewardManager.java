package uk.firedev.daisylib.reward;

import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.reward.types.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class RewardManager {

    private static RewardManager instance = null;

    private final Map<String, RewardType> rewardTypes = new HashMap<>();
    private boolean loaded = false;

    private RewardManager() {}

    public static RewardManager getInstance() {
        if (instance == null) {
            instance = new RewardManager();
        }
        return instance;
    }

    public void load() {
        if (!isLoaded()) {
            new ItemRewardType().register();
            new CommandRewardType().register();
            new PermissionRewardType().register();
            new ExpRewardType().register();
            loaded = true;
        }
    }

    public boolean isLoaded() { return loaded; }

    /**
     * Register a custom reward type.
     * @param rewardType The reward type instance you wish to register
     * @return Whether the reward type was added or not
     */
    public boolean registerRewardType(RewardType rewardType) {
        String identifier = rewardType.getIdentifier();
        if (rewardTypes.containsKey(identifier.toUpperCase())) {
            return false;
        }
        Loggers.log(Level.INFO, rewardType.getPlugin().getLogger(), "Registered " + rewardType.getIdentifier() + " RewardType by " + rewardType.getAuthor());
        rewardTypes.put(identifier.toUpperCase(), rewardType);
        return true;
    }

    public Collection<RewardType> getRegisteredRewardTypes() {
        return rewardTypes.values();
    }

}

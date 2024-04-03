package uk.firedev.daisylib.reward;

import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.types.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            new HealthRewardType().register();
            new MoneyRewardType().register();
            new TeleportRewardType().register();
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
        DaisyLib.getInstance().getLogger().info("Registered " + rewardType.getIdentifier() + " RewardType by " + rewardType.getAuthor() + " from the plugin " + rewardType.getPlugin().getName());
        rewardTypes.put(identifier.toUpperCase(), rewardType);
        return true;
    }

    public List<RewardType> getRegisteredRewardTypes() {
        return new ArrayList<>(rewardTypes.values());
    }

}

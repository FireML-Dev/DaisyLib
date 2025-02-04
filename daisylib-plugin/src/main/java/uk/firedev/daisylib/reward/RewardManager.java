package uk.firedev.daisylib.reward;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.types.*;

import java.util.*;
import java.util.function.BiConsumer;

public class RewardManager {

    private static RewardManager instance = null;

    private final TreeMap<String, RewardType> rewardTypes = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
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
            Loggers.info(DaisyLib.getInstance().getComponentLogger(), "Loading RewardManager!");
            new ItemRewardType().register();
            new CommandRewardType().register();
            new PermissionRewardType().register();
            new ExpRewardType().register();
            new HealthRewardType().register();
            new MoneyRewardType().register();
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
        if (rewardTypes.containsKey(identifier)) {
            return false;
        }
        Loggers.info(DaisyLib.getInstance().getComponentLogger(),
                "<green>Registered <gold>" + identifier + "</gold> RewardType by <gold>" + rewardType.getAuthor() + "</gold> from the plugin <aqua>" + rewardType.getPlugin().getName()
        );
        rewardTypes.put(identifier, rewardType);
        return true;
    }

    /**
     * Unregister a custom reward type.
     * @param rewardName The reward you wish to unregister
     * @return Whether the reward type was removed or not
     */
    public boolean unregisterRewardType(@NotNull String rewardName) {
        if (!rewardTypes.containsKey(rewardName)) {
            return false;
        }
        rewardTypes.remove(rewardName);
        return true;
    }

    public @Nullable RewardType getRewardType(@NotNull String identifier) {
        return rewardTypes.get(identifier);
    }

    public List<RewardType> getRegisteredRewardTypes() {
        return new ArrayList<>(rewardTypes.values());
    }

    /**
     * Alternative method of registering reward types.
     * @param identifier The type's identifier
     * @param author The author of this reward type
     * @param plugin The plugin responsible for this reward type
     * @param checkLogic The code to run when checking the reward. The String will be the provided value to check against.
     * @return Whether this type was registered or not
     */
    public boolean registerRewardType(@NotNull String identifier, @NotNull String author, @NotNull Plugin plugin, @NotNull BiConsumer<Player, String> checkLogic) {

        return registerRewardType(new RewardType() {
            @Override
            public void doReward(@NotNull Player player, @NotNull String value) {
                checkLogic.accept(player, value);
            }

            @Override
            public @NotNull String getIdentifier() {
                return identifier;
            }

            @Override
            public @NotNull String getAuthor() {
                return author;
            }

            @Override
            public @NotNull Plugin getPlugin() {
                return plugin;
            }
        });

    }

    /**
     * @return A read-only copy of the reward type map
     */
    public Map<String, RewardType> getRewardTypeMap() {
        return Map.copyOf(rewardTypes);
    }

}

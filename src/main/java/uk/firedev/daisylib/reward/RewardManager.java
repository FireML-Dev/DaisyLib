package uk.firedev.daisylib.reward;

import org.apache.commons.lang3.function.TriFunction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.types.*;
import uk.firedev.daisylib.utils.ItemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

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
            Loggers.info(DaisyLib.getInstance().getComponentLogger(), "Loading RewardManager!");
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
        Loggers.info(DaisyLib.getInstance().getComponentLogger(),
                "<green>Registered <gold>" + rewardType.getIdentifier() + "</gold> RewardType by <gold>" + rewardType.getAuthor() + "</gold> from the plugin <aqua>" + rewardType.getPlugin().getName()
        );
        rewardTypes.put(identifier.toUpperCase(), rewardType);
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
    public boolean registerRewardType(@NotNull String identifier, @NotNull String author, @NotNull Plugin plugin, @NotNull BiConsumer<@NotNull Player, @NotNull String> checkLogic) {

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

    public Map<String, RewardType> getRewardTypeMap() {
        return new HashMap<>(rewardTypes);
    }

}

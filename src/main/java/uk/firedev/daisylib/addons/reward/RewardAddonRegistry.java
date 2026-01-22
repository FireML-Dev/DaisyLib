package uk.firedev.daisylib.addons.reward;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.addons.InvalidAddonException;
import uk.firedev.daisylib.registry.Registry;
import uk.firedev.daisylib.util.Loggers;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class RewardAddonRegistry implements Registry<RewardAddon> {

    private static final RewardAddonRegistry instance = new RewardAddonRegistry();

    private final Map<String, RewardAddon> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private RewardAddonRegistry() {}

    public static @NonNull RewardAddonRegistry get() {
        return instance;
    }

    @NonNull
    @Override
    public Map<String, RewardAddon> getRegistry() {
        return Map.copyOf(registry);
    }

    @Nullable
    @Override
    public RewardAddon get(@NonNull String key) {
        return registry.get(key);
    }

    @NonNull
    @Override
    public RewardAddon getOrDefault(@NonNull String key, @NonNull RewardAddon defaultValue) {
        return registry.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean unregister(@NonNull String key) {
        return registry.remove(key) != null;
    }

    @Override
    public boolean register(@NonNull RewardAddon value, boolean force) {
        if (!force && registry.containsKey(value.getKey())) {
            return false;
        }
        registry.put(value.getKey(), value);
        return true;
    }

    public void processString(@Nullable String string, @Nullable Player player) {
        if (string == null || player == null) {
            return;
        }
        String[] split = string.split(":");
        String name;
        String rewardInput;
        try {
            name = split[0];
            rewardInput = String.join(":", Arrays.copyOfRange(split, 1, split.length));
        } catch (ArrayIndexOutOfBoundsException exception) {
            Loggers.warn(RewardAddon.class, "Failed to process a RewardAddon String! \"" + string + "\" is not formatted correctly.", new InvalidRewardException());
            return;
        }
        RewardAddon addon = get(name);
        if (addon == null) {
            Loggers.warn(RewardAddon.class, "Failed to process a RewardAddon String! \"" + name + "\" is not a valid RewardAddon.", new InvalidAddonException());
            return;
        }
        addon.doReward(player, rewardInput);
    }

    /**
     * Checks if this registry is empty.
     *
     * @return Whether this registry is empty.
     */
    @Override
    public boolean isEmpty() {
        return registry.isEmpty();
    }

    /**
     * Removes all items from this registry.
     */
    @Override
    public void clear() {
        registry.clear();
    }
    
}

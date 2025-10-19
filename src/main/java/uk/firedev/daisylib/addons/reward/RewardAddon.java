package uk.firedev.daisylib.addons.reward;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.addons.Addon;
import uk.firedev.daisylib.addons.InvalidAddonException;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public abstract class RewardAddon extends Addon {

    private static final TreeMap<String, RewardAddon> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * @deprecated Use {@link #getRegistry()} instead.
     */
    @Deprecated(forRemoval = true)
    public static Map<String, RewardAddon> getLoadedAddons() {
        return getRegistry();
    }

    public static Map<String, RewardAddon> getRegistry() {
        return Map.copyOf(registry);
    }

    public static @Nullable RewardAddon get(@NotNull String identifier) {
        return registry.get(identifier);
    }

    public static boolean unregister(@NotNull String identifier) {
        if (!registry.containsKey(identifier)) {
            return false;
        }
        registry.remove(identifier);
        return true;
    }

    public static void processString(@Nullable String string, @Nullable Player player) {
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

    public RewardAddon() {}

    public abstract void doReward(@NotNull Player player, @NotNull String value);

    public boolean register() {
        return register(false);
    }

    public boolean register(boolean force) {
        if (!force && registry.containsKey(getIdentifier())) {
            return false;
        }
        registry.put(getIdentifier(), this);
        return true;
    }

    public boolean unregister() {
        return unregister(getIdentifier());
    }

}

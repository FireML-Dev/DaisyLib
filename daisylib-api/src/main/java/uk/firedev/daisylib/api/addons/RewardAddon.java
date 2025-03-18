package uk.firedev.daisylib.api.addons;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.addons.exceptions.InvalidAddonException;
import uk.firedev.daisylib.api.addons.exceptions.InvalidRewardException;

import java.util.Map;
import java.util.TreeMap;

public abstract class RewardAddon extends Addon {

    private static final TreeMap<String, RewardAddon> loadedAddons = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public static Map<String, RewardAddon> getLoadedAddons() {
        return Map.copyOf(loadedAddons);
    }

    public static @Nullable RewardAddon get(@NotNull String identifier) {
        return loadedAddons.get(identifier);
    }

    public static boolean unregister(@NotNull String identifier) {
        if (!loadedAddons.containsKey(identifier)) {
            return false;
        }
        loadedAddons.remove(identifier);
        return true;
    }

    public static void processString(@Nullable String string, @Nullable Player player) {
        if (string == null || player == null) {
            return;
        }
        String[] split = string.split("=");
        String name;
        String rewardInput;
        try {
            name = split[0];
            rewardInput = split[1];
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
        if (loadedAddons.containsKey(getIdentifier())) {
            return false;
        }
        loadedAddons.put(getIdentifier(), this);
        return true;
    }

    public boolean unregister() {
        return unregister(getIdentifier());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RewardAddon addon)) {
            return false;
        }
        return getIdentifier().equals(addon.getIdentifier());
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

}

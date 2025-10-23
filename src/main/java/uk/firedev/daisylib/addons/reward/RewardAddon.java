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
        RewardAddon addon = RewardAddonRegistry.get().get(name);
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
        return RewardAddonRegistry.get().register(this, force);
    }

    public boolean unregister() {
        return RewardAddonRegistry.get().unregister(this);
    }

}

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

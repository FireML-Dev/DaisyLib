package uk.firedev.daisylib.addons.reward;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.Addon;

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

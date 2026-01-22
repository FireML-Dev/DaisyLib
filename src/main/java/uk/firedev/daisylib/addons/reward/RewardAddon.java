package uk.firedev.daisylib.addons.reward;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.Addon;

public abstract class RewardAddon extends Addon {

    public RewardAddon() {}

    public abstract void doReward(@NonNull Player player, @NonNull String value);

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

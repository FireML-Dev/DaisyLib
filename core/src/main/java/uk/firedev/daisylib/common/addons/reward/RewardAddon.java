package uk.firedev.daisylib.common.addons.reward;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.addons.Addon;

/**
 * A way to reward a player for certain actions.
 */
public abstract class RewardAddon extends Addon {

    public RewardAddon() {}

    public abstract void give(@NonNull Player player, @NonNull String value, @Nullable Location location);

    public abstract @NonNull String getKey();

    public abstract @NonNull String getAuthor();

    public abstract @NonNull Plugin getPlugin();

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
package uk.firedev.daisylib.common.addons.reward;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.common.registry.RegistryItem;

/**
 * A way to reward a player for certain actions.
 */
public abstract class RewardAddon implements RegistryItem {

    public RewardAddon() {}

    public abstract void doReward(@NonNull Player player, @NonNull String key, @NonNull String value, Location hookLocation);

    public abstract @NonNull String getIdentifier();

    @Override
    public @NonNull String getKey() {
        return getIdentifier();
    }

    public abstract @NonNull String getAuthor();

    public abstract @NonNull Plugin getPlugin();

    public boolean register() {
        return RewardAddonRegistry.get().register(this);
    }

    public boolean unregister() {
        return RewardAddonRegistry.get().unregister(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RewardAddon type)) {
            return false;
        }
        return getIdentifier().equals(type.getIdentifier());
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

}
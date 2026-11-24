package uk.firedev.daisylib.reward;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.registry.RegistryItem;

/// A way to reward a player for certain actions.
@NullMarked
public abstract class RewardType implements RegistryItem {

    public RewardType() {}

    public abstract void give(Player player, String value, @Nullable Location location);

    public abstract String getIdentifier();

    @Override
    public String getKey() {
        return getIdentifier();
    }

    public abstract String getAuthor();

    public abstract Plugin getPlugin();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RewardType type)) {
            return false;
        }
        return getIdentifier().equals(type.getIdentifier());
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

}
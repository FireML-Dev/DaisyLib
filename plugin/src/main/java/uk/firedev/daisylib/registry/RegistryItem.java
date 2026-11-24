package uk.firedev.daisylib.registry;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class RegistryItem {

    /// @return The identifier of this registry item. Case-insensitive.
    public abstract String getKey();

    /// @return The plugin that added this registry item.
    public abstract Plugin getPlugin();

    /// @return Whether all dependencies for this item are present.
    public abstract boolean checkDependencies();

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RegistryItem item)) {
            return false;
        }
        return getKey().equals(item.getKey());
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

}

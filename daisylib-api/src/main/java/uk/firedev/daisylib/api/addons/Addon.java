package uk.firedev.daisylib.api.addons;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public abstract class Addon {

    public abstract @NotNull String getIdentifier();

    public abstract @NotNull Plugin getOwningPlugin();

    public abstract @NotNull String getAuthor();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemAddon addon)) {
            return false;
        }
        return getIdentifier().equals(addon.getIdentifier());
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

}

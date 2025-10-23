package uk.firedev.daisylib.addons;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.item.ItemAddon;
import uk.firedev.daisylib.registry.RegistryItem;

import java.util.UUID;

public abstract class Addon implements RegistryItem {

    public abstract @NotNull Plugin getPlugin();

    public abstract @NotNull String getAuthor();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemAddon addon)) {
            return false;
        }
        return getKey().equals(addon.getKey());
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

}

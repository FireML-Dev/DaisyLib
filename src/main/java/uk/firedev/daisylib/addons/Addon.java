package uk.firedev.daisylib.addons;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.logging.Logging;
import uk.firedev.daisylib.registry.RegistryItem;

public abstract class Addon implements RegistryItem {

    private final Logging logging = Logging.logging(this.getClass());

    public @NonNull Logging getLogging() {
        return logging;
    }

    public abstract @NonNull Plugin getPlugin();

    public abstract @NonNull String getAuthor();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Addon addon)) {
            return false;
        }
        return getKey().equals(addon.getKey());
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

}

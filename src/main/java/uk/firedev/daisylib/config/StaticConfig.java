package uk.firedev.daisylib.config;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;

import java.io.File;

/**
 * A wrapper for a YAML config file. Saves the default resource if provided and allows the file to be read.
 * <p>
 * Provides no update functionality. For automatic config updates, use {@link UpdatableConfig}.
 */
public class StaticConfig extends ConfigBase {

    public StaticConfig(@NonNull File file, @NonNull Plugin plugin) {
        super(file, null, plugin);
    }

    public StaticConfig(@NonNull String fileName, @NonNull Plugin plugin) {
        super(fileName, null, plugin);
    }

    public StaticConfig(@NonNull String fileName, @NonNull String resourceName, @NonNull Plugin plugin) {
        super(fileName, resourceName, plugin);
    }

    @Override
    public final void update() {}

}

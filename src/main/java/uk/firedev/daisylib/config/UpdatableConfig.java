package uk.firedev.daisylib.config;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;

import java.io.File;

/**
 * A simple config wrapper. Saves the default resource if provided and allows the file to be read.
 * <p>
 * Allows custom update/migration logic to be applied. If the config has no version, updates are skipped.
 */
public abstract class UpdatableConfig extends ConfigBase {

    public UpdatableConfig(@NonNull File file, @NonNull String resourceName, @NonNull Plugin plugin) {
        super(file, resourceName, plugin);
    }

    public UpdatableConfig(@NonNull String fileName, @NonNull String resourceName, @NonNull Plugin plugin) {
        super(fileName, resourceName, plugin);
    }

    @Override
    public boolean copyDefaults() {
        return true;
    }

}

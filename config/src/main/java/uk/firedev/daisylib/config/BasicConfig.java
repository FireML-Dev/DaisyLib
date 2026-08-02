package uk.firedev.daisylib.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;

import java.io.File;

/**
 * A simple config wrapper. Saves the default resource if provided and allows the file to be read.
 * <p>
 * Config updates are not supported with this type.
 */
public final class BasicConfig extends ConfigBase {

    public BasicConfig(@NonNull File file, @NonNull Plugin plugin) {
        super(file, null, plugin);
    }

    public BasicConfig(@NonNull String fileName, @NonNull String resourceName, @NonNull Plugin plugin) {
        super(fileName, resourceName, plugin);
    }

    public BasicConfig() {
        super();
    }

    @Override
    public boolean copyDefaults() {
        return true;
    }

    @Override
    public boolean allowUpdate() {
        return false;
    }

    @Override
    public void updateConfig(@NonNull YamlConfiguration config, int targetVersion) { /* Intentionally does nothing. */ }

}

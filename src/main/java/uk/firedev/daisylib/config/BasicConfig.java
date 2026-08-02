package uk.firedev.daisylib.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.config.settings.UpdateSettings;

import java.io.File;

/**
 * A simple config wrapper. Saves the default resource if provided and allows the file to be read.
 * <p>
 * Config updates are not supported with this type.
 */
public class BasicConfig extends ConfigBase {

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
    public @Nullable UpdateSettings getUpdateSettings() {
        return null;
    }

    @Override
    public boolean copyDefaults() {
        return true;
    }

}

package uk.firedev.daisylib.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.DaisyLib;
import uk.firedev.daisylib.common.logging.Logging;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

public abstract class ConfigBase {

    private final Logging logging;
    private final boolean preventIO;
    private final String resourceName;
    private final Plugin plugin;

    private YamlConfiguration config = new YamlConfiguration();
    private File file = null;

    public ConfigBase(@NonNull File file, @Nullable String resourceName, @NonNull Plugin plugin) {
        this.config.options().copyDefaults(allowUpdate());
        this.preventIO = false;
        this.resourceName = resourceName;
        this.plugin = plugin;
        this.logging = Logging.logging(plugin);
        reload(file);
        update();
    }

    public ConfigBase(@NonNull String fileName, @NonNull String resourceName, @NonNull Plugin plugin) {
        this(
            new File(plugin.getDataFolder(), fileName),
            resourceName,
            plugin
        );
    }

    /**
     * Creates an instance of ConfigBase with a blank file. This disables all I/O methods.
     * <p>
     * Uses the plugin that initialized DaisyLib.
     */
    public ConfigBase() {
        this.preventIO = true;
        this.resourceName = null;
        this.plugin = DaisyLib.get().getPlugin();
        this.logging = Logging.logging(plugin);
    }

    /**
     * Attempts to create the chosen file. If an exception is thrown, it will be logged and ignored.
     */
    private void createFile(@NonNull File configFile) {
        if (preventIO || configFile.exists()) {
            return;
        }
        try (InputStream resource = resourceName == null ? null : plugin.getResource(resourceName)) {
            File parent = configFile.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            if (resource != null) {
                Files.copy(resource, configFile.toPath());
            } else {
                configFile.createNewFile();
            }
        } catch (IOException exception) {
            logging.warn("Failed to create " + configFile.getName(), exception);
        }
    }

    public final void reload(@NonNull File configFile) {
        if (preventIO) {
            return;
        }
        createFile(configFile);
        try (InputStreamReader resource = fetchResource()) {
            this.config.load(configFile);
            this.file = configFile;
            if (resource != null) {
                this.config.setDefaults(YamlConfiguration.loadConfiguration(resource));
            }
        } catch (IOException | InvalidConfigurationException exception) {
            logging.warn("Failed to load resource " + resourceName);
        }
    }

    public final void reload() {
        if (preventIO || file == null) {
            return;
        }
        reload(this.file);
    }

    public final @NonNull YamlConfiguration getConfig() {
        if (this.config == null) {
            throw new IllegalStateException("Config is not loaded.");
        }
        return this.config;
    }

    public final @Nullable File getFile() { return this.file; }

    public final @NonNull Plugin getPlugin() { return this.plugin; }

    public final @Nullable String getResourceName() { return this.resourceName; }

    public final void save() {
        if (preventIO || this.file == null) {
            return;
        }
        try {
            getConfig().save(this.file);
        } catch (IOException exception) {
            logging.warn("Failed to save " + this.file.getName(), exception);
        }
    }

    /**
     * Attempts to update this config using the default values and any custom logic.
     */
    public final void update() {
        if (preventIO || !allowUpdate() || this.file == null) {
            return;
        }
        Configuration defaults = getConfig().getDefaults();
        if (defaults == null) {
            return;
        }
        int expectedVersion = defaults.getInt("version", -1); // Example 1
        int currentVersion = getConfig().getInt("version", -1); // Example 5

        if (expectedVersion == -1) {
            return;
        }
        if (currentVersion == -1) {
            logging.warn("Unknown config version. Skipping updates.");
            return;
        }

        // Current version is above expected, do nothing as we can't downgrade.
        if (currentVersion > expectedVersion) {
            logging.warn("Downgrading configs is not supported, so updates will not be performed. Some configs may be broken.");
            return;
        }

        // Current version is not equal to expected, perform updates.
        if (currentVersion != expectedVersion) {
            updateConfig(getConfig(), expectedVersion);
            getConfig().set("version", expectedVersion);
            save();
        }
    }

    private @Nullable InputStreamReader fetchResource() {
        if (resourceName == null) {
            return null;
        }
        InputStream resource = plugin.getResource(resourceName);
        if (resource == null) {
            return null;
        }
        return new InputStreamReader(resource);
    }

    public abstract boolean allowUpdate();

    public abstract void updateConfig(@NonNull YamlConfiguration config, int targetVersion);

}

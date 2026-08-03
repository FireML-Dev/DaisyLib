package uk.firedev.daisylib.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.logging.Logging;
import uk.firedev.daisylib.messages.config.PaperConfigReader;
import uk.firedev.daisylib.messages.message.ComponentMessage;
import uk.firedev.daisylib.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class ConfigBase {

    protected final PaperConfigReader reader;

    protected final Logging logging;
    protected final boolean preventIO;
    protected final String resourceName;
    protected final Plugin plugin;

    protected YamlConfiguration config = new YamlConfiguration();
    protected File file = null;

    public ConfigBase(@NonNull File file, @Nullable String resourceName, @NonNull Plugin plugin) {
        this.preventIO = false;
        this.resourceName = resourceName;
        this.plugin = plugin;
        this.logging = Logging.logging(plugin);
        this.reader = new PaperConfigReader(getConfig());
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
        this.reader = new PaperConfigReader(getConfig());
    }

    public final void reload(@NonNull File configFile) {
        if (preventIO) {
            return;
        }
        FileUtils.loadFile(configFile, resourceName, plugin);
        try {
            this.config.load(configFile);
            this.file = configFile;
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
     * Custom update logic.
     */
    public abstract void update();

    public ComponentMessage<?, ?> getComponentMessage(@NonNull String path, @NonNull Object def) {
        ComponentMessage<?, ?> message = ComponentMessage.componentMessage(reader, path);
        return message == null ? ComponentMessage.componentMessage(def) : message;
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

    /**
     * Copies the default values to the file.
     * <p>
     * Works by inserting all file keys into the default config and saving to disk.
     */
    protected void copyDefaults() {
        if (resourceName == null) {
            return;
        }
        try (InputStreamReader resource = fetchResource()) {
            if (resource == null) {
                return;
            }
            YamlConfiguration newConfig = YamlConfiguration.loadConfiguration(resource);
            for (String key : newConfig.getKeys(true)) {
                if (!this.config.isSet(key)) {
                    logging.debug("Key " + key + " is not set in file. Skipping.");
                    continue;
                }
                logging.debug("Key " + key + " existed in file. Copying.");
                newConfig.set(key, this.config.get(key));
            }
            this.config = newConfig;
            newConfig.save(this.file);
        } catch (IOException exception) {
            logging.error("Failed to copy default values to " + file.getName());
        }
    }

}

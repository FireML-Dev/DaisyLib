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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

public abstract class ConfigBase {

    protected final PaperConfigReader reader;

    protected final Logging logging;
    protected final boolean preventIO;
    protected final String resourceName;
    protected final Plugin plugin;
    protected final YamlConfiguration config = new YamlConfiguration();

    protected File file = null;

    public ConfigBase(@NonNull File file, @Nullable String resourceName, @NonNull Plugin plugin) {
        this.config.options().copyDefaults(copyDefaults());
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

    /**
     * Should missing default values be copied into this config?
     */
    public abstract boolean copyDefaults();

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

}

package uk.firedev.daisylib;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

public class Config {

    private final String fileName;
    private final JavaPlugin plugin;

    private FileConfiguration config = null;
    private File file = null;

    /**
     * Creates an instance of the Config class.
     * @param fileName The name of the config file to use.
     * @param plugin The plugin associated with the config file.
     * @param configUpdater Should the config updater be used?
     */
    @Deprecated(forRemoval = true)
    public Config(@NotNull String fileName, @NotNull JavaPlugin plugin, boolean configUpdater) {
        this.fileName = fileName;
        this.plugin = plugin;
        reload();
        if (configUpdater) {
            updateConfig(false);
        }
    }

    /**
     * Creates an instance of the Config class.
     * @param fileName The name of the config file to use.
     * @param plugin The plugin associated with the config file.
     * @param configUpdater Should the config updater be used?
     * @param removeUnusedConfig Should config options that are not in the default file be removed? Only applies if configUpdater is true.
     */
    public Config(@NotNull String fileName, @NotNull JavaPlugin plugin, boolean configUpdater, boolean removeUnusedConfig) {
        this.fileName = fileName;
        this.plugin = plugin;
        reload();
        if (configUpdater) {
            updateConfig(removeUnusedConfig);
        }
    }

    public void reload() {
        File configFile = FileUtils.loadFile(getPlugin().getDataFolder(), this.fileName, getPlugin());
        if (configFile == null) {
            return;
        }

        FileConfiguration config = new YamlConfiguration();

        try {
            config.load(configFile);
            this.config = config;
            this.file = configFile;
        } catch (IOException | InvalidConfigurationException e) {
            Loggers.logException(e, plugin.getLogger());
        }
    }

    public @NotNull Plugin getPlugin() { return this.plugin; }

    public @NotNull FileConfiguration getConfig() { return this.config; }

    public @NotNull File getFile() { return file; }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void updateConfig(boolean removeUnusedConfig) {
        File tempDirectory = new File(this.plugin.getDataFolder(), "temp");
        File tempConfigFile = FileUtils.loadFile(tempDirectory, fileName, getPlugin());
        if (tempConfigFile == null) {
            return;
        }

        FileConfiguration tempConfig = new YamlConfiguration();

        try {
            tempConfig.load(tempConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            return;
        }

        config.getKeys(true).forEach(key -> {
            // Don't set keys that aren't in the default config if removeUnusedConfig is true.
            if (removeUnusedConfig && !tempConfig.isSet(key)) {
                return;
            }
            if (!config.isConfigurationSection(key)) {
                tempConfig.set(key, config.get(key));
            }
            tempConfig.setComments(key, config.getComments(key));
        });
        try {
            tempConfig.save(file);
            tempConfigFile.delete();
        } catch (IOException ex) {
            Loggers.logException(ex, plugin.getLogger());
        }
        reload();
    }

}


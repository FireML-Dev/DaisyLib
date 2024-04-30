package uk.firedev.daisylib;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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

    public Config(@NotNull String fileName, @NotNull JavaPlugin plugin, boolean configUpdater) {
        this.fileName = fileName;
        this.plugin = plugin;
        reload();
        if (configUpdater) {
            updateConfig();
        }
    }

    public void reload() {
        File configFile = loadFile(this.plugin.getDataFolder());
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
    private File loadFile(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File configFile = new File(directory, fileName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                Loggers.logException(e, plugin.getLogger());
            }

            InputStream stream = plugin.getResource(fileName);
            if (stream == null) {
                Loggers.log(Level.SEVERE, plugin.getLogger(), "Could not retrieve " + fileName);
                return null;
            }
            try {
                Files.copy(stream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Loggers.logException(e, plugin.getLogger());
            }
            return configFile;
        }
        return configFile;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void updateConfig() {
        File tempDirectory = new File(this.plugin.getDataFolder(), "temp");
        File tempConfigFile = loadFile(tempDirectory);
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
            // Don't set keys that aren't in the default config
            if (!tempConfig.isSet(key)) {
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


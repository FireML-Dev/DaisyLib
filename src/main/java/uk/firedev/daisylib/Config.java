package uk.firedev.daisylib;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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

    public Config(String fileName, JavaPlugin plugin) {
        this.fileName = fileName;
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        File configFile = new File(this.plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            File parentFile = configFile.getAbsoluteFile().getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                Loggers.logException(e, plugin.getLogger());
            }

            InputStream stream = plugin.getResource(fileName);
            if (stream == null) {
                Loggers.log(Level.SEVERE, plugin.getLogger(), "Could not retrieve " + fileName);
                return;
            }
            try {
                Files.copy(stream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Loggers.logException(e, plugin.getLogger());
            }
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

    public FileConfiguration getConfig() { return this.config; }

    public File getFile() { return file; }

}


package uk.firedev.daisylib;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.message.component.ComponentMessage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ConfigBase {

    private final @NotNull File file;
    private final @NotNull Plugin plugin;
    private final @NotNull YamlConfiguration fileConfig = new YamlConfiguration();
    private final @Nullable YamlConfiguration resourceConfig;
    private final @NotNull Map<Integer, Consumer<YamlConfiguration>> updateMap = new HashMap<>();

    private boolean enableConfigUpdates = false;

    public ConfigBase(@NotNull String fileName, @Nullable String resourceName, @NotNull Plugin plugin) {
        this(
                new File(plugin.getDataFolder(), fileName),
                resourceName,
                plugin
        );
    }

    public ConfigBase(@NotNull File file, @Nullable String resourceName, @NotNull Plugin plugin) {
        this.file = file;
        this.resourceConfig = loadResourceConfig(resourceName, plugin);
        this.plugin = plugin;
    }

    private YamlConfiguration loadResourceConfig(@Nullable String resourceName, @NotNull Plugin plugin) {
        if (resourceName == null) {
            return null;
        }
        InputStream resource = plugin.getResource(resourceName);
        if (resource == null) {
            return null;
        }
        return YamlConfiguration.loadConfiguration(new InputStreamReader(resource));
    }

    // ConfigBase Settings

    public ConfigBase addConfigUpdate(int version, Consumer<YamlConfiguration> update) {
        updateMap.putIfAbsent(version, update);
        return this;
    }

    public ConfigBase enableConfigUpdates(boolean enabled) {
        this.enableConfigUpdates = enabled;
        return this;
    }

    // Loading and Reloading

    public void init() {
        reload();

        if (enableConfigUpdates && resourceConfig != null) {
            int fileVersion = fileConfig.getInt("config-version", -1);
            int resourceVersion = resourceConfig.getInt("config-version", -1);
            if (resourceVersion == -1) {
                Loggers.error(plugin.getComponentLogger(), "Resource config version not found.");
                return;
            }
            performConfigUpdates(fileVersion, resourceVersion);
        }
    }

    public void reload() {
        try {
            fileConfig.load(file);
        } catch (IOException | InvalidConfigurationException | IllegalArgumentException exception) {
            Loggers.error(plugin.getComponentLogger(), "Failed to reload " + file.getName(), exception);
        }
    }

    private void performConfigUpdates(int fileVersion, int resourceVersion) {
        if (resourceVersion <= fileVersion) {
            return;
        }
        while (resourceVersion > fileVersion) {
            Consumer<YamlConfiguration> update = updateMap.get(fileVersion);
            if (update != null) {
                update.accept(fileConfig);
            }
            fileVersion++;
        }
        fileConfig.set("config-version", resourceVersion);
        save();
    }

    public void save() {
        try {
            fileConfig.save(file);
        } catch (IOException exception) {
            Loggers.error(plugin.getComponentLogger(), "Failed to save " + file.getName(), exception);
        }
    }

    // Getters

    public @NotNull YamlConfiguration getConfig() {
        return fileConfig;
    }

    public @NotNull File getFile() {
        return file;
    }

    public @NotNull Plugin getPlugin() {
        return plugin;
    }

    public @Nullable YamlConfiguration getResourceConfig() {
        return resourceConfig;
    }

    // Config Getters

    public ComponentMessage getComponentMessage(@NotNull String path, @NotNull Component def) {
        return ComponentMessage.fromConfig(getConfig(), path, def);
    }

    public ComponentMessage getComponentMessage(@NotNull String path, @NotNull String def) {
        return ComponentMessage.fromConfig(getConfig(), path, MiniMessage.miniMessage().deserialize(def));
    }

}

package uk.firedev.daisylib.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.Settings;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.message.component.ComponentMessage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigBase {

    private final @NotNull File file;
    private final @NotNull Plugin plugin;
    private final @NotNull YamlConfiguration fileConfig = new YamlConfiguration();

    private final @Nullable YamlConfiguration resourceConfig;
    private final @Nullable String resourceName;

    private @Nullable UpdaterSettings updaterSettings = null;

    public ConfigBase(@NotNull String fileName, @Nullable String resourceName, @NotNull Plugin plugin) {
        this(
                new File(plugin.getDataFolder(), fileName),
                resourceName,
                plugin
        );
    }

    public ConfigBase(@NotNull File file, @Nullable String resourceName, @NotNull Plugin plugin) {
        this.file = file;
        this.plugin = plugin;
        this.resourceName = resourceName;
        this.resourceConfig = loadResourceConfig();
    }

    private YamlConfiguration loadResourceConfig() {
        InputStream resource = getResource();
        if (resource == null) {
            return null;
        }
        return YamlConfiguration.loadConfiguration(new InputStreamReader(resource));
    }

    // ConfigBase Settings

    /**
     * Adds custom UpdaterSettings to this config.
     * @param settings The UpdaterSettings to use
     * @param versionKey The version key to check
     */
    public ConfigBase withUpdaterSettings(@NotNull UpdaterSettings settings, @NotNull String versionKey) {
        UpdaterSettings.Builder builder = UpdaterSettings.builder(settings);
        builder.setVersioning(new BasicVersioning(versionKey));
        this.updaterSettings = builder.build();
        return this;
    }

    /**
     * Adds custom UpdaterSettings to this config.
     * @param settings The UpdaterSettings to use
     */
    public ConfigBase withUpdaterSettings(@NotNull UpdaterSettings settings) {
        this.updaterSettings = settings;
        return this;
    }

    /**
     * Adds the default UpdaterSettings to this config.
     */
    public ConfigBase withDefaultUpdaterSettings() {
        this.updaterSettings = UpdaterSettings.builder()
                .setVersioning(new BasicVersioning("config-version"))
                .build();
        return this;
    }

    // Loading and Reloading

    public void init() {
        reload();
    }

    public void reload() {
        performBoostedYamlUpdates();
        try {
            fileConfig.load(file);
        } catch (IOException | InvalidConfigurationException | IllegalArgumentException exception) {
            Loggers.error(plugin.getComponentLogger(), "Failed to reload " + file.getName(), exception);
        }
    }

    private void performBoostedYamlUpdates() {

        List<Settings> settingsList = new ArrayList<>(Arrays.asList(
            GeneralSettings.builder().setUseDefaults(false).build(),
            DumperSettings.DEFAULT,
            LoaderSettings.DEFAULT
        ));

        if (updaterSettings != null) {
            settingsList.add(updaterSettings);
        }

        try {
            YamlDocument document;
            InputStream resource = getResource();
            if (resource == null) {
                document = YamlDocument.create(file, settingsList.toArray(Settings[]::new));
            } else {
                document = YamlDocument.create(file, resource, settingsList.toArray(Settings[]::new));
            }
            if (updaterSettings != null) {
                document.update();
            }
            document.save();
        } catch (IOException exception) {
            Loggers.logException(plugin.getComponentLogger(), exception);
        }
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

    public @Nullable InputStream getResource() {
        return resourceName == null ? null : plugin.getResource(resourceName);
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

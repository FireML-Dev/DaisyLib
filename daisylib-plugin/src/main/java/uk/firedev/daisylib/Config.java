package uk.firedev.daisylib;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.Settings;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.message.component.ComponentMessage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    private final boolean allowIO;
    private final String fileName;
    private final String resourceName;
    private final Plugin plugin;
    private final boolean configUpdater;

    private YamlDocument config = null;
    private File file = null;

    /**
     * Creates an instance of the Config class.
     * @param fileName The name of the config file to use.
     * @param plugin The plugin associated with the config file.
     * @param configUpdater Should the config updater be used?
     */
    public Config(@NotNull String fileName, @NotNull String resourceName, @NotNull Plugin plugin, boolean configUpdater) {
        this.allowIO = true;
        this.fileName = fileName;
        this.resourceName = resourceName;
        this.plugin = plugin;
        this.configUpdater = configUpdater;
        reload(new File(getPlugin().getDataFolder(), getFileName()));
        update();
    }

    public Config(@NotNull File file, @NotNull Plugin plugin, boolean configUpdater) {
        this.allowIO = true;
        this.fileName = file.getName();
        this.resourceName = null;
        this.plugin = plugin;
        this.configUpdater = configUpdater;
        reload(file);
        update();
    }

    /**
     * Creates an empty instance of the Config class.
     * All I/O methods are disabled.
     */
    public Config(@NotNull Plugin plugin) throws IOException {
        this.allowIO = false;
        this.fileName = null;
        this.resourceName = null;
        this.plugin = plugin;
        this.configUpdater = false;
        this.config = YamlDocument.create(InputStream.nullInputStream());
    }

    public void reload() {
        if (!allowIO || this.file == null) {
            return;
        }
        reload(this.file);
    }

    public void reload(@NotNull File file) {
        if (!allowIO) {
            return;
        }

        try {
            InputStream resource = getResourceName() == null ? null : getPlugin().getResource(getResourceName());
            if (resource == null) {
                this.config = YamlDocument.create(file, getSettings());
            } else {
                this.config = YamlDocument.create(file, resource, getSettings());
            }
            this.file = file;
            if (configUpdater) {
                this.config.update();
            }
        } catch (IOException ex) {
            Loggers.logException(plugin.getComponentLogger(), ex);
        }
    }

    public @NotNull YamlDocument getConfig() { return this.config; }

    public @NotNull Plugin getPlugin() { return this.plugin; }

    public @NotNull File getFile() { return file; }

    public String getFileName() { return this.fileName; }

    public String getResourceName() { return this.resourceName; }

    public Settings[] getSettings() {
        List<Settings> settingsList = new ArrayList<>(Arrays.asList(
                getGeneralSettings(),
                getDumperSettings(),
                getLoaderSettings()
        ));

        if (configUpdater) {
            settingsList.add(getUpdaterSettings());
        }

        return settingsList.toArray(Settings[]::new);
    }

    public GeneralSettings getGeneralSettings() {
        return GeneralSettings.builder().setUseDefaults(false).build();
    }

    public DumperSettings getDumperSettings() {
        return DumperSettings.DEFAULT;
    }

    public LoaderSettings getLoaderSettings() {
        return LoaderSettings.DEFAULT;
    }

    public UpdaterSettings getUpdaterSettings() {
        return UpdaterSettings.builder()
                .setEnableDowngrading(false)
                .setKeepAll(true)
                .setVersioning(new BasicVersioning("config-version"))
                .build();
    }

    public ComponentMessage getComponentMessage(@NotNull String path, @NotNull String def) {
        return ComponentMessage.fromConfig(getConfig(), path, MiniMessage.miniMessage().deserialize(def));
    }

    public void save() {
        if (!allowIO) {
            return;
        }
        try {
            getConfig().save();
        } catch (IOException exception) {
            Loggers.warn(plugin.getComponentLogger(), "Failed to save " + getFileName());
        }
    }

    public void update() {
        if (!allowIO || !configUpdater) {
            return;
        }
        try {
            getConfig().update();
        } catch (IOException exception) {
            Loggers.warn(plugin.getComponentLogger(), "Failed to update " + getFileName());
        }
    }

}


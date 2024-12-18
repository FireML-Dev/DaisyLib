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
import uk.firedev.daisylib.message.component.ComponentMessage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class Config {

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
        this.fileName = fileName;
        this.resourceName = resourceName;
        this.plugin = plugin;
        this.configUpdater = configUpdater;
        reload();
    }

    public void reload() {
        // BoostedYAML handles the file creation for us
        File configFile = new File(getPlugin().getDataFolder(), getFileName());

        List<Settings> settingsList = new ArrayList<>(Arrays.asList(
                getGeneralSettings(),
                getDumperSettings()
        ));

        if (configUpdater) {
            settingsList.add(getLoaderSettings());
            settingsList.add(getUpdaterSettings());
        }

        final Settings[] settings = settingsList.toArray(new Settings[0]);

        try {
            InputStream resource = getPlugin().getResource(getResourceName());
            if (resource == null) {
                this.config = YamlDocument.create(configFile, settings);
            } else {
                this.config = YamlDocument.create(configFile, resource, settings);
            }
            this.file = configFile;
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

    public GeneralSettings getGeneralSettings() {
        return GeneralSettings.builder().setUseDefaults(false).build();
    }

    public DumperSettings getDumperSettings() {
        return DumperSettings.DEFAULT;
    }

    public LoaderSettings getLoaderSettings() {
        return LoaderSettings.builder().setAutoUpdate(true).build();
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

}


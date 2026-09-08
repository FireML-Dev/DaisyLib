package uk.firedev.daisylib.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A wrapper for a YAML config file. Saves the default resource if provided and allows the file to be read.
 * <p>
 * Applies custom update/migration logic (skipped if no version exists), then fills in missing keys from defaults.
 */
public abstract class UpdatableConfig extends ConfigBase {

    public UpdatableConfig(@NonNull File file, @NonNull String resourceName, @NonNull Plugin plugin) {
        super(file, resourceName, plugin);
    }

    public UpdatableConfig(@NonNull String fileName, @NonNull Plugin plugin) {
        super(fileName, null, plugin);
    }

    public UpdatableConfig(@NonNull String fileName, @NonNull String resourceName, @NonNull Plugin plugin) {
        super(fileName, resourceName, plugin);
    }

    public abstract @NonNull Settings getUpdateSettings();

    public abstract @NonNull String versionKey();

    @Override
    public void update() {
        if (preventIO || this.file == null) {
            return;
        }

        Configuration defaults = getConfig().getDefaults();
        if (defaults == null) {
            return;
        }
        // Copy missing keys from the default config.
        copyDefaults();

        Settings settings = getUpdateSettings();

        // Apply any updates that do not depend on file version.
        settings.updates.forEach(update -> update.accept(getConfig()));

        int expectedVersion = defaults.getInt(versionKey(), -1);
        int currentVersion = getConfig().getInt(versionKey(), -1);

        if (expectedVersion == -1) {
            return;
        }
        if (currentVersion == -1) {
            logging.warn("Unknown config version. Skipping updates.");
            return;
        }

        // Current version is above expected. We can't downgrade, so do nothing.
        if (currentVersion > expectedVersion) {
            logging.warn("Downgrading configs is not supported, so updates will not be performed. Some configs may be broken.");
            return;
        }

        // Apply updates that require a file version.
        if (currentVersion != expectedVersion) {
            int v = currentVersion;
            while (v < expectedVersion) {
                v++;
                List<Consumer<YamlConfiguration>> updates = settings.versionedUpdates.get(v);
                if (updates != null) {
                    updates.forEach(update -> update.accept(getConfig()));
                }
            }
            getConfig().set(versionKey(), expectedVersion);
            save();
        }
    }

    private void copyDefaults() {
        if (resourceName == null) {
            return;
        }
        try {
            for (String key : defaultConfig.getKeys(true)) {
                if (this.config.isSet(key)) {
                    logging.debug("Key " + key + " is already set in file. Skipping.");
                    continue;
                }
                logging.debug("Key " + key + " did not exist in file. Copying.");
                this.config.set(key, defaultConfig.get(key));
            }
            this.config.save(this.file);
        } catch (IOException exception) {
            logging.error("Failed to copy default values to " + file.getName());
        }
    }

    public static class Settings {

        protected Map<Integer, List<Consumer<YamlConfiguration>>> versionedUpdates = new HashMap<>();
        protected List<Consumer<YamlConfiguration>> updates = new ArrayList<>();

        public void addCustomLogic(int version, @NonNull Consumer<YamlConfiguration> logic) {
            versionedUpdates.computeIfAbsent(version, ArrayList::new).add(logic);
        }

        public void addRelocation(int version, @NonNull String from, @NonNull String to) {
            addCustomLogic(version, config -> ConfigUtils.move(config, from, to));
        }

        public void addRelocations(int version, @NonNull Map<@NonNull String, @NonNull String> relocations) {
            addCustomLogic(version, config ->
                relocations.forEach((from, to) ->
                    ConfigUtils.move(config, from, to)
                )
            );
        }

        public void addRemoval(int version, @NonNull String path) {
            addCustomLogic(version, config -> config.set(path, null));
        }

        public void addRemovals(int version, @NonNull List<String> removals) {
            addCustomLogic(version, config ->
                removals.forEach(path ->
                    config.set(path, null)
                )
            );
        }

    }

}

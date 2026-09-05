package uk.firedev.daisylib.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A wrapper for a YAML config file. Saves the default resource if provided and allows the file to be read.
 * <p>
 * Applies custom update/migration logic (skipped if no version exists), then fills in missing keys from defaults.
 */
public abstract class UpdatableConfig extends ConfigBase {

    private final Map<@NonNull Integer, @NonNull Consumer<YamlConfiguration>> customUpdates = new HashMap<>();

    public UpdatableConfig(@NonNull File file, @NonNull String resourceName, @NonNull Plugin plugin) {
        super(file, resourceName, plugin);
    }

    public UpdatableConfig(@NonNull String fileName, @NonNull Plugin plugin) {
        super(fileName, null, plugin);
    }

    public UpdatableConfig(@NonNull String fileName, @NonNull String resourceName, @NonNull Plugin plugin) {
        super(fileName, resourceName, plugin);
    }

    public void addCustomUpdateLogic(int version, @NonNull Consumer<@NonNull YamlConfiguration> logic) {
        customUpdates.put(version, logic);
    }

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
        copyDefaults();

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

        // Current version is not equal to expected. Perform our updates.
        if (currentVersion != expectedVersion) {
            int v = currentVersion;
            while (v < expectedVersion) {
                v++;
                Consumer<YamlConfiguration> update = customUpdates.get(v);
                if (update != null) {
                    update.accept(getConfig());
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
        try (InputStreamReader resource = fetchResource()) {
            if (resource == null) {
                return;
            }
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(resource);
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

}

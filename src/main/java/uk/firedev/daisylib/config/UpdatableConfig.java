package uk.firedev.daisylib.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A simple config wrapper. Saves the default resource if provided and allows the file to be read.
 * <p>
 * Allows custom update/migration logic to be applied. If the config has no version, updates are skipped.
 */
public abstract class UpdatableConfig extends ConfigBase {

    private final Map<@NonNull Integer, @NonNull Consumer<YamlConfiguration>> customUpdates = new HashMap<>();

    public UpdatableConfig(@NonNull File file, @NonNull String resourceName, @NonNull Plugin plugin) {
        super(file, resourceName, plugin);
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

}

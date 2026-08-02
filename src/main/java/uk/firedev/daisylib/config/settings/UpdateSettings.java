package uk.firedev.daisylib.config.settings;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jspecify.annotations.NonNull;

public abstract class UpdateSettings {

    public abstract boolean allowUpdate();

    public abstract @NonNull String versionKey();

    public abstract void updateConfig(@NonNull YamlConfiguration config, int targetVersion);

}

package uk.firedev.daisylib.messages.config;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class PaperConfigReader implements ConfigReader<ConfigurationSection> {

    private final ConfigurationSection config;

    public PaperConfigReader(@NotNull ConfigurationSection section) {
        this.config = section;
    }

    @Override
    public @Nullable Object getObject(String path) {
        return config.get(path);
    }

    @Override
    public @Nullable String getString(String path) {
        return config.getString(path);
    }

    @Override
    public @NotNull List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    @Override
    public @NotNull ConfigurationSection getConfig() {
        return config;
    }

    @Override
    public @Nullable ConfigReader<ConfigurationSection> getSection(@NotNull String path) {
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) {
            return null;
        }
        return new PaperConfigReader(section);
    }

}

package uk.firedev.daisylib.configurate;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.spongepowered.configurate.yaml.internal.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ConfigurateConfigBase {

    private final @NotNull File file;
    private final @NotNull YamlConfigurationLoader loader;
    private @NotNull CommentedConfigurationNode root;

    /**
     * Creates a new ConfigurateConfigBase instance.
     * This constructor does not support config updates.
     * @param file The file to use.
     */
    public ConfigurateConfigBase(@NotNull File file) throws IOException {
        this.file = file;

        this.loader = YamlConfigurationLoader.builder().path(file.toPath()).build();
        this.root = loader.load();
    }

    /**
     * Creates a new ConfigurateConfigBase instance.
     * This constructor supports config updates.
     * @param file The file to use.
     * @param defaults The defaults to use.
     */
    public ConfigurateConfigBase(@NotNull File file, @NotNull InputStream defaults, @NotNull String versionKey) throws IOException {
        try (defaults) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                Files.copy(defaults, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            this.file = file;

            this.loader = YamlConfigurationLoader.builder().path(file.toPath()).build();
            this.root = loader.load();

            performConfigUpdate(defaults, versionKey);
        }
    }

    private void performConfigUpdate(@NotNull InputStream defaults, @NotNull String versionKey) {
        Map<String, Object> data = new Yaml().load(defaults);

        int defaultsVersion;
        try {
            defaultsVersion = (int) data.get(versionKey);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Version key must be an integer!", e);
        }

        // While root node version is less than the version in the defaults
        int currentVersion = getInt(versionKey);
        while (currentVersion < defaultsVersion) {
            Consumer<CommentedConfigurationNode> update = getConfigUpdate(currentVersion);
            if (update != null) {
                update.accept(root);
            }
            currentVersion++;
        }
        setInt(versionKey, currentVersion);
        save();
    }

    public abstract Consumer<CommentedConfigurationNode> getConfigUpdate(int version);

    // IO Methods

    public void save() {
        try {
            loader.save(root);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to save config file: " + file.getName(), exception);
        }
    }

    public void reload() {
        try {
            this.root = loader.load();
        } catch (IOException exception) {
            throw new RuntimeException("Failed to reload config file: " + file.getName(), exception);
        }
    }

    // Getters and Setters

    public String getString(@NotNull String path) {
        return NodeUtils.get(root, path, String.class);
    }

    public String getStringOrDefault(@NotNull String path, @NotNull String defaultValue) {
        String value = getString(path);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public void setString(@NotNull String path, @NotNull String value) {
        NodeUtils.set(root, path, String.class, value);
    }

    public Integer getInt(@NotNull String path) {
        return NodeUtils.get(root, path, Integer.class);
    }

    public int getIntOrDefault(@NotNull String path, int defaultValue) {
        Integer value = getInt(path);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public void setInt(@NotNull String path, int value) {
        NodeUtils.set(root, path, int.class, value);
    }

    public Boolean getBoolean(@NotNull String path) {
        return NodeUtils.get(root, path, Boolean.class);
    }

    public boolean getBooleanOrDefault(@NotNull String path, boolean defaultValue) {
        Boolean value = getBoolean(path);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public void setBoolean(@NotNull String path, boolean value) {
        NodeUtils.set(root, path, boolean.class, value);
    }

}


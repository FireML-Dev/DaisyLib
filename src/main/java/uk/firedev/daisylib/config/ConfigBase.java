package uk.firedev.daisylib.config;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.configlib.ConfigFile;
import uk.firedev.daisylib.util.Utils;
import uk.firedev.messagelib.config.PaperConfigLoader;
import uk.firedev.messagelib.message.ComponentMessage;

import java.io.File;
import java.io.InputStream;

public class ConfigBase extends ConfigFile {

    private final PaperConfigLoader loader;

    public ConfigBase(@NotNull String filePath, @NotNull String resourcePath, @NotNull Plugin plugin) {
        super(filePath, resourcePath, plugin);
        this.loader = new PaperConfigLoader(config);
    }

    public ConfigBase(@NotNull String filePath, @NotNull Plugin plugin) {
        super(filePath, plugin);
        this.loader = new PaperConfigLoader(config);
    }

    public ConfigBase(@NotNull File file) {
        super(file);
        this.loader = new PaperConfigLoader(config);
    }

    public ConfigBase(@NotNull File file, @Nullable InputStream resource) {
        super(file, resource);
        this.loader = new PaperConfigLoader(config);
    }

    public ConfigBase(@NotNull File file, @NotNull String resourcePath, @NotNull Plugin plugin) {
        super(file, resourcePath, plugin);
        this.loader = new PaperConfigLoader(config);
    }

    /**
     * @deprecated No longer needed.
     */
    @Deprecated(forRemoval = true)
    public void init() {}

    public @NotNull PaperConfigLoader getMessageLoader() {
        return this.loader;
    }

    public @Nullable Float getFloat(@NotNull String path) {
        return getConfig().getObject(path, Float.class);
    }

    public float getFloat(@NotNull String path, float def) {
        Float value = getFloat(path);
        return value == null ? def : value;
    }

    public ComponentMessage getComponentMessage(@NotNull String path, @NotNull Object def) {
        ComponentMessage message = ComponentMessage.componentMessage(getMessageLoader(), path);
        return message == null ? ComponentMessage.componentMessage(def) : message;
    }

    public @Nullable Sound getSound(@NotNull String path) {
        ConfigurationSection section = getConfig().getConfigurationSection(path);
        if (section == null) {
            return null;
        }
        String keyStr = section.getString("sound");
        if (keyStr == null) {
            return null;
        }
        Key key = NamespacedKey.fromString(keyStr);
        if (key == null) {
            return null;
        }
        Sound.Source source = Utils.getEnumValue(Sound.Source.class, section.getString("source"), Sound.Source.PLAYER);
        float volume = getFloat("volume", 1.0F);
        float pitch = getFloat("pitch", 1.0F);
        return Sound.sound(key, source, volume, pitch);
    }

}

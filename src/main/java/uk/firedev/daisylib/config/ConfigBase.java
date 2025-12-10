package uk.firedev.daisylib.config;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.configlib.ConfigFile;
import uk.firedev.daisylib.utils.ObjectUtils;
import uk.firedev.messagelib.config.PaperConfigLoader;
import uk.firedev.messagelib.message.ComponentMessage;

import java.io.File;

public class ConfigBase extends ConfigFile {

    private final PaperConfigLoader messageLoader;

    public ConfigBase(@NotNull String fileName, @Nullable String resourceName, @NotNull Plugin plugin) {
        this(
                new File(plugin.getDataFolder(), fileName),
                resourceName,
                plugin
        );
    }

    public ConfigBase(@NotNull File file, @Nullable String resourceName, @NotNull Plugin plugin) {
        super(
            file,
            resourceName == null ? null : plugin.getResource(resourceName)
        );
        this.messageLoader = new PaperConfigLoader(getConfig());
    }

    public @NotNull PaperConfigLoader getMessageLoader() {
        return this.messageLoader;
    }

    @Override
    public void performManualUpdates() {}

    // Config Getters

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
        Sound.Source source = ObjectUtils.getEnumValue(Sound.Source.class, section.getString("source"), Sound.Source.PLAYER);
        float volume = getFloat("volume", 1.0F);
        float pitch = getFloat("pitch", 1.0F);
        return Sound.sound(key, source, volume, pitch);
    }

}

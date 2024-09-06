package uk.firedev.daisylib.message.string;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.message.Message;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;

import java.util.List;

public class StringMessage implements Message {

    private @NotNull String message;

    /**
     * @deprecated Use {@link StringMessage#of(String)} instead. This constructor will be made private for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public StringMessage(@NotNull String message) {
        this.message = message;
    }

    public static StringMessage of(@NotNull String message) {
        return new StringMessage(message);
    }

    public static StringMessage ofOrDefault(@Nullable String message, @NotNull String def) {
        return message == null ? of(def) : of(message);
    }

    public static StringMessage fromComponent(@NotNull Component message) {
        return ComponentMessage.of(message).toStringMessage();
    }

    public static StringMessage fromComponent(@Nullable Component message, @NotNull String def) {
        if (message == null) {
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Invalid message supplied. Using the default value.");
            return of(def);
        } else {
            return ComponentMessage.of(message).toStringMessage();
        }
    }

    public static StringMessage fromComponentMessage(@NotNull ComponentMessage componentMessage) {
        return componentMessage.toStringMessage();
    }

    public static StringMessage fromConfig(@NotNull YamlDocument config, @NotNull String path, @NotNull String def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        return fromConfigString(message, def, path);
    }

    public static StringMessage fromConfig(@NotNull FileConfiguration config, @NotNull String path, @NotNull String def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        return fromConfigString(message, def, path);
    }

    private static StringMessage fromConfigString(@Nullable String message, @NotNull String def, @NotNull String path) {
        final String finalMessage;
        if (message == null) {
            finalMessage = def;
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Invalid message at " + path + ". Using the default value.");
        } else {
            finalMessage = message;
        }
        return of(finalMessage);
    }

    public StringMessage applyReplacer(@Nullable StringReplacer replacer) {
        if (replacer != null) {
            this.message = replacer.replace(this.message);
        }
        return this;
    }

    public StringMessage replace(@NotNull String placeholder, @NotNull String replacement) {
        return applyReplacer(new StringReplacer().addReplacement(placeholder, replacement));
    }

    @Override
    public void sendMessage(@NotNull Audience audience) {
        toComponentMessage().sendMessage(audience);
    }

    @Override
    public void sendMessage(@NotNull List<Audience> audienceList) {
        toComponentMessage().sendMessage(audienceList);
    }

    @Override
    public void sendActionBar(@NotNull Audience audience) {
        toComponentMessage().sendActionBar(audience);
    }

    @Override
    public void broadcast() {
        toComponentMessage().broadcast();
    }

    public @NotNull String getMessage() {
        return this.message;
    }

    public ComponentMessage toComponentMessage() {
        return ComponentMessage.fromStringMessage(this);
    }

    public StringMessage parsePAPI(@NotNull OfflinePlayer player) {
        if (DaisyLib.getInstance().isPluginEnabled("PlaceholderAPI")) {
            this.message = PlaceholderAPI.setPlaceholders(player, this.message);
        }
        return this;
    }

    public StringMessage addPrefix(@NotNull String prefix) {
        this.message = prefix + this.message;
        return this;
    }

    public StringMessage addPrefix(@NotNull StringMessage prefix) {
        this.message = prefix.getMessage() + this.message;
        return this;
    }

    public StringMessage append(@NotNull String append) {
        this.message = this.message + append;
        return this;
    }

    public StringMessage append(@NotNull StringMessage append) {
        this.message = this.message + append.getMessage();
        return this;
    }

    public StringMessage duplicate() {
        return of(this.message);
    }

    // Deprecated Constructors

    /**
     * @deprecated Use {@link StringMessage#fromConfig(YamlDocument, String, String)} instead. This constructor will be made private for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public StringMessage(@NotNull YamlDocument config, @NotNull String path, @NotNull String def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        if (message == null) {
            this.message = def;
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Invalid message at " + path + ". Using the default value.");
        } else {
            this.message = message;
        }
    }

    /**
     * @deprecated Use {@link StringMessage#fromConfig(FileConfiguration, String, String)} instead. This constructor will be made private for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public StringMessage(@NotNull FileConfiguration config, @NotNull String path, @NotNull String def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        if (message == null) {
            this.message = def;
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Invalid message at " + path + ". Using the default value.");
        } else {
            this.message = message;
        }
    }

    /**
     * @deprecated Use {@link StringMessage#ofOrDefault(String, String)} instead. This constructor will be made private for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public StringMessage(@Nullable String message, @NotNull String def) {
        if (message == null) {
            this.message = def;
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Invalid message supplied. Using the default value.");
        } else {
            this.message = message;
        }
    }

    /**
     * @deprecated Use {@link StringMessage#fromComponentMessage(ComponentMessage)} instead. This constructor will be made private for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public StringMessage(@NotNull ComponentMessage componentMessage) {
        this.message = MiniMessage.miniMessage().serialize(componentMessage.getMessage());
    }

}

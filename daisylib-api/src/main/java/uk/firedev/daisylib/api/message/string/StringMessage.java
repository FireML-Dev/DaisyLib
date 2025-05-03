package uk.firedev.daisylib.api.message.string;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.message.Message;
import uk.firedev.daisylib.api.message.component.ComponentMessage;

import java.util.List;
import java.util.regex.Matcher;

public class StringMessage implements Message {

    private static LegacyComponentSerializer legacyComponentSerializer = null;

    private @NotNull String message;

    private StringMessage(@NotNull String message) {
        this.message = message;
    }

    /**
     * Required for the {@link #parsePlaceholderAPI(OfflinePlayer)} method. Should not be used elsewhere unless absolutely necessary.
     * @return A Section LegacyComponentSerializer
     */
    public static LegacyComponentSerializer getLegacyComponentSerializer() {
        if (legacyComponentSerializer == null) {
            legacyComponentSerializer = LegacyComponentSerializer.legacySection();
        }
        return legacyComponentSerializer;
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
            Loggers.warn(StringMessage.class, "Invalid message supplied. Using the default value.");
            return of(def);
        } else {
            return ComponentMessage.of(message).toStringMessage();
        }
    }

    public static StringMessage fromComponentMessage(@NotNull ComponentMessage componentMessage) {
        return componentMessage.toStringMessage();
    }

    public static StringMessage fromConfig(@NotNull YamlConfiguration config, @NotNull String path, @NotNull String def) {
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
            Loggers.warn(StringMessage.class, "Invalid message at " + path + ". Using the default value.");
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
        return applyReplacer(StringReplacer.create(placeholder, replacement));
    }

    @Override
    public void sendMessage(@Nullable Audience audience) {
        toComponentMessage().sendMessage(audience);
    }

    @Override
    public void sendMessage(@NotNull List<Audience> audienceList) {
        toComponentMessage().sendMessage(audienceList);
    }

    @Override
    public void sendActionBar(@Nullable Audience audience) {
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

    public StringMessage parsePlaceholderAPI(@Nullable OfflinePlayer player) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Matcher matcher = PlaceholderAPI.getPlaceholderPattern().matcher(this.message);
            while (matcher.find()) {
                // Find matched String
                String matched = matcher.group();
                // Convert to Legacy Component and into a MiniMessage String
                String parsed = ComponentMessage.getMiniMessage().serialize(
                        getLegacyComponentSerializer().deserialize(
                                PlaceholderAPI.setPlaceholders(player, matched)
                        )
                );
                // Escape matched String so we don't have issues
                String safeMatched = Matcher.quoteReplacement(matched);
                // Replace all instances of the matched String with the parsed placeholder.
                this.message = this.message.replaceAll(safeMatched, parsed);
            }
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

    @Override
    public int getLength() {
        return message.length();
    }

}

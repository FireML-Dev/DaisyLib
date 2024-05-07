package uk.firedev.daisylib.message.string;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.message.Message;
import uk.firedev.daisylib.message.component.ComponentMessage;

import java.util.List;

public class StringMessage implements Message {

    private @NotNull String message;

    public StringMessage(@NotNull FileConfiguration config, @NotNull String path, @NotNull String def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        if (message == null) {
            this.message = def;
            DaisyLib.getInstance().getLogger().warning("Invalid message at " + path + ". Using the default value.");
        } else {
            this.message = message;
        }
    }

    public StringMessage(@Nullable String message, @NotNull String def) {
        if (message == null) {
            this.message = def;
            DaisyLib.getInstance().getLogger().warning("Invalid message supplied. Using the default value.");
        } else {
            this.message = message;
        }
    }

    public StringMessage(@NotNull ComponentMessage componentMessage) {
        this.message = MiniMessage.miniMessage().serialize(componentMessage.getMessage());
    }

    public StringMessage(@NotNull String message) {
        this.message = message;
    }

    public StringMessage applyReplacer(@Nullable StringReplacer replacer) {
        if (replacer != null) {
            this.message = replacer.replace(this.message);
        }
        return this;
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
        return new ComponentMessage(this);
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

}

package uk.firedev.daisylib.message.component;

import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.message.Message;
import uk.firedev.daisylib.message.string.StringMessage;

import java.util.List;

public class ComponentMessage implements Message {

    private @NotNull Component message;

    /**
     * @deprecated Use {@link ComponentMessage#of(Component)} instead. This constructor will be made private for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public ComponentMessage(@NotNull Component message) {
        this.message = message;
    }

    public static ComponentMessage of(@NotNull Component message) {
        return new ComponentMessage(message);
    }

    public static ComponentMessage fromString(@NotNull String message) {
        return of(MiniMessage.miniMessage().deserialize(message));
    }

    public static ComponentMessage fromString(@Nullable String message, @NotNull Component def) {
        final Component finalMessage;
        if (message == null) {
            finalMessage = def;
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Invalid message supplied. Using the default value.");
        } else {
            finalMessage = MiniMessage.miniMessage().deserialize(message);
        }
        return of(finalMessage);
    }

    public static ComponentMessage fromStringMessage(@NotNull StringMessage stringMessage) {
        return fromString(stringMessage.getMessage());
    }

    public static ComponentMessage fromConfig(@NotNull YamlDocument config, @NotNull String path, @NotNull Component def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        return fromConfigString(message, def, path);
    }

    public static ComponentMessage fromConfig(@NotNull FileConfiguration config, @NotNull String path, @NotNull Component def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        return fromConfigString(message, def, path);
    }

    private static ComponentMessage fromConfigString(@Nullable String message, @NotNull Component def, @NotNull String path) {
        final Component finalMessage;
        if (message == null) {
            finalMessage = def;
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Invalid message at " + path + ". Using the default value.");
        } else {
            finalMessage = MiniMessage.miniMessage().deserialize(message);
        }
        return of(finalMessage);
    }

    public ComponentMessage applyReplacer(@Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            this.message = replacer.replace(this.message);
        }
        return this;
    }

    @Override
    public void sendMessage(@NotNull Audience audience) {
        audience.sendMessage(getMessage());
    }

    @Override
    public void sendMessage(@NotNull List<Audience> audienceList) {
        audienceList.forEach(this::sendMessage);
    }

    @Override
    public void sendActionBar(@NotNull Audience audience) {
        audience.sendActionBar(getMessage());
    }

    @Override
    public void broadcast() {
        Bukkit.broadcast(getMessage());
    }

    public @NotNull Component getMessage() {
        return this.message.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public StringMessage toStringMessage() {
        return new StringMessage(this);
    }

    public String toPlainText() {
        return PlainTextComponentSerializer.plainText().serialize(this.message);
    }

    public String toJSON() {
        return JSONComponentSerializer.json().serialize(this.message);
    }

    public ComponentMessage stripStyling() {
        this.message = Component.text(toPlainText());
        return this;
    }

    public ComponentMessage parseMiniPlaceholders(@NotNull Audience audience) {
        if (DaisyLib.getInstance().isPluginEnabled("MiniPlaceholders")) {
            String stringMessage = MiniMessage.miniMessage().serialize(getMessage());
            TagResolver resolver = TagResolver.builder()
                    .resolver(TagResolver.standard())
                    .resolver(MiniPlaceholders.getAudiencePlaceholders(audience))
                    .resolver(MiniPlaceholders.getGlobalPlaceholders())
                    .build();
            this.message = MiniMessage.miniMessage().deserialize(stringMessage, resolver);
        }
        return this;
    }

    public boolean matchesString(@NotNull String matcher) {
        return toPlainText().equals(matcher);
    }

    public boolean isEmpty() {
        return toPlainText().isEmpty();
    }

    public ComponentMessage addPrefix(@NotNull Component prefix) {
        this.message = prefix.append(this.message);
        return this;
    }

    public ComponentMessage addPrefix(@NotNull String prefix) {
        this.message = fromString(prefix).getMessage().append(this.message);
        return this;
    }

    public ComponentMessage addPrefix(@NotNull ComponentMessage prefix) {
        this.message = prefix.getMessage().append(this.message);
        return this;
    }

    public ComponentMessage addPrefix(@NotNull StringMessage prefix) {
        return addPrefix(prefix.toComponentMessage());
    }

    public ComponentMessage append(@NotNull Component append) {
        this.message = this.message.append(append);
        return this;
    }

    public ComponentMessage append(@NotNull ComponentMessage append) {
        this.message = this.message.append(append.getMessage());
        return this;
    }

    public ComponentMessage duplicate() {
        return of(this.message);
    }

    public static ComponentMessage getHoverItem(ItemStack item) {
        if (item == null || item.isEmpty()) {
            return fromString("<white>[None]</white>");
        }
        Component quantity = Component.text(item.getAmount() + "x ");
        Component hover = quantity.append(item.displayName());
        hover = hover.hoverEvent(item);
        return of(hover);
    }

    public static ComponentMessage getMainHandHoverItem(@NotNull Player player) {
        return getHoverItem(player.getInventory().getItemInMainHand());
    }

    public static ComponentMessage getOffHandHoverItem(@NotNull Player player) {
        return getHoverItem(player.getInventory().getItemInOffHand());
    }

    // Deprecated constructors - To be removed in 2.0.4-SNAPSHOT

    /**
     * @deprecated Use {@link ComponentMessage#fromConfig(YamlDocument, String, Component)} instead. This constructor will be removed for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public ComponentMessage(@NotNull YamlDocument config, @NotNull String path, @NotNull Component def) {
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
            this.message = MiniMessage.miniMessage().deserialize(message);
        }
    }

    /**
     * @deprecated Use {@link ComponentMessage#fromConfig(YamlDocument, String, Component)} instead. This constructor will be removed for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public ComponentMessage(@NotNull YamlDocument config, @NotNull String path, @NotNull String def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        if (message == null) {
            this.message = MiniMessage.miniMessage().deserialize(def);
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Invalid message at " + path + ". Using the default value.");
        } else {
            this.message = MiniMessage.miniMessage().deserialize(message);
        }
    }

    /**
     * @deprecated Use {@link ComponentMessage#fromConfig(FileConfiguration, String, Component)} instead. This constructor will be removed for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public ComponentMessage(@NotNull FileConfiguration config, @NotNull String path, @NotNull Component def) {
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
            this.message = MiniMessage.miniMessage().deserialize(message);
        }
    }

    /**
     * @deprecated Use {@link ComponentMessage#fromConfig(FileConfiguration, String, Component)} instead. This constructor will be removed for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public ComponentMessage(@NotNull FileConfiguration config, @NotNull String path, @NotNull String def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        if (message == null) {
            this.message = MiniMessage.miniMessage().deserialize(def);
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Invalid message at " + path + ". Using the default value.");
        } else {
            this.message = MiniMessage.miniMessage().deserialize(message);
        }
    }

    /**
     * @deprecated Use {@link ComponentMessage#fromString(String, Component)} instead. This constructor will be removed for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public ComponentMessage(@Nullable String message, @NotNull Component def) {
        if (message == null) {
            this.message = def;
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Invalid message supplied. Using the default value.");
        } else {
            this.message = MiniMessage.miniMessage().deserialize(message);
        }
    }

    /**
     * @deprecated Use {@link ComponentMessage#fromStringMessage(StringMessage)} instead. This constructor will be removed for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public ComponentMessage(@NotNull StringMessage stringMessage) {
        this.message = MiniMessage.miniMessage().deserialize(stringMessage.getMessage());
    }

    /**
     * @deprecated Use {@link ComponentMessage#fromString(String)} instead. This constructor will be removed for 2.0.4-SNAPSHOT.
     */
    @Deprecated(forRemoval = true)
    public ComponentMessage(@NotNull String message) {
        this.message = MiniMessage.miniMessage().deserialize(message);
    }

}

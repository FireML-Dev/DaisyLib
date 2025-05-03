package uk.firedev.daisylib.api.message.component;

import io.github.miniplaceholders.api.MiniPlaceholders;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.message.Message;
import uk.firedev.daisylib.api.message.string.StringMessage;
import uk.firedev.daisylib.api.utils.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

public class ComponentMessage implements Message {

    private @NotNull Component message;
    private @NotNull MessageType messageType;

    private static LegacyComponentSerializer legacyComponentSerializer = null;
    private static MiniMessage miniMessage = null;
    private static JSONComponentSerializer jsonComponentSerializer = null;

    private ComponentMessage(@NotNull Component message, @NotNull MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public static ComponentMessage of(@NotNull Component message, @NotNull MessageType messageType) {
        return new ComponentMessage(message, messageType);
    }

    public static ComponentMessage of(@NotNull Component message) {
        return of(message, MessageType.CHAT);
    }

    public static ComponentMessage ofOrDefault(@Nullable Component message, @NotNull Component def, @NotNull MessageType messageType) {
        return message == null ? of(def, messageType) : of(message, messageType);
    }

    public static ComponentMessage ofOrDefault(@Nullable Component message, @NotNull Component def) {
        return ofOrDefault(message, def, MessageType.CHAT);
    }

    public static ComponentMessage fromPlainText(@NotNull String plainText, @NotNull MessageType messageType) {
        return of(Component.text(plainText), messageType);
    }

    public static ComponentMessage fromPlainText(@NotNull String plainText) {
        return fromPlainText(plainText, MessageType.CHAT);
    }

    public static ComponentMessage fromJson(@NotNull String json, @NotNull MessageType messageType) {
        return of(getJsonComponentSerializer().deserialize(json), messageType);
    }

    public static ComponentMessage fromJson(@NotNull String json) {
        return fromJson(json, MessageType.CHAT);
    }

    public static ComponentMessage fromString(@NotNull String message, @NotNull MessageType messageType) {
        return of(getMiniMessage().deserialize(message), messageType);
    }

    public static ComponentMessage fromString(@NotNull String message) {
        return fromString(message, MessageType.CHAT);
    }

    public static ComponentMessage fromString(@Nullable String message, @NotNull Component def, @NotNull MessageType messageType) {
        if (message == null) {
            Loggers.warn(ComponentMessage.class, "Invalid message supplied. Using the default value.");
            return of(def, messageType);
        }
        return fromString(message, messageType);
    }

    public static ComponentMessage fromString(@Nullable String message, @NotNull Component def) {
        return fromString(message, def, MessageType.CHAT);
    }

    public static ComponentMessage fromStringMessage(@NotNull StringMessage stringMessage) {
        return fromString(stringMessage.getMessage()).setMessageType(stringMessage.getMessageType());
    }

    public static ComponentMessage fromConfig(@NotNull ConfigurationSection config, @NotNull String path, @NotNull Component def) {
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) {
            return processConfig(config.get(path), path, def);
        } else {
            MessageType type = ObjectUtils.getEnumValue(
                MessageType.class,
                section.getString("type", "CHAT"),
                MessageType.CHAT
            );
            return processConfig(section.getString("message"), path, def).setMessageType(type);
        }
    }

    public static ComponentMessage fromConfig(@NotNull ConfigurationSection config, @NotNull String path, @NotNull String def) {
        return fromConfig(
            config,
            path,
            getMiniMessage().deserialize(def)
        );
    }

    private static ComponentMessage processConfig(@Nullable Object object, @NotNull String path, @NotNull Component def) {
        if (object == null) {
            return fromConfigString(null, def, path);
        }
        if (object instanceof List<?> list) {
            List<String> strings = list.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .toList();
            return fromConfigString(String.join("\n",strings), def, path);
        } else {
            return fromConfigString(object.toString(), def, path);
        }
    }

    private static ComponentMessage fromConfigString(@Nullable String message, @NotNull Component def, @NotNull String path) {
        final Component finalMessage;
        if (message == null) {
            finalMessage = def;
            Loggers.warn(ComponentMessage.class, "Invalid message at " + path + ". Using the default value.");
        } else {
            finalMessage = getMiniMessage().deserialize(message);
        }
        return of(finalMessage);
    }

    public static MiniMessage getMiniMessage() {
        if (miniMessage == null) {
            miniMessage = MiniMessage.miniMessage();
        }
        return miniMessage;
    }

    public static LegacyComponentSerializer getLegacyComponentSerializer() {
        if (legacyComponentSerializer == null) {
            legacyComponentSerializer = LegacyComponentSerializer.legacySection();
        }
        return legacyComponentSerializer;
    }

    public static JSONComponentSerializer getJsonComponentSerializer() {
        if (jsonComponentSerializer == null) {
            jsonComponentSerializer = JSONComponentSerializer.json();
        }
        return jsonComponentSerializer;
    }

    public ComponentMessage applyReplacer(@Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            this.message = replacer.replace(this.message);
        }
        return this;
    }

    public ComponentMessage replace(@NotNull String placeholder, @NotNull String replacement) {
        return applyReplacer(ComponentReplacer.create(placeholder, replacement));
    }

    public ComponentMessage replace(@NotNull String placeholder, @NotNull Component replacement) {
        return applyReplacer(ComponentReplacer.create(placeholder, replacement));
    }

    @Override
    public void sendMessage(@Nullable Audience audience) {
        if (audience != null) {
            switch (messageType) {
                case CHAT -> audience.sendMessage(getMessage());
                case ACTION_BAR -> audience.sendActionBar(getMessage());
                case TITLE -> {
                    Title title = Title.title(getMessage(), Component.empty());
                    audience.showTitle(title);
                }
                case SUBTITLE -> {
                    Title title = Title.title(Component.empty(), getMessage());
                    audience.showTitle(title);
                }
            }
        }
    }

    @Override
    public void broadcast() {
        Bukkit.broadcast(getMessage());
    }

    public @NotNull Component getMessage() {
        return this.message.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public String toPlainText() {
        return PlainTextComponentSerializer.plainText().serialize(this.message);
    }

    public String toJson() {
        return getJsonComponentSerializer().serialize(this.message);
    }

    public ComponentMessage stripStyling() {
        this.message = Component.text(toPlainText());
        return this;
    }

    public ComponentMessage parseMiniPlaceholders(@NotNull Audience audience) {
        if (Bukkit.getPluginManager().isPluginEnabled("MiniPlaceholders")) {
            String stringMessage = getMiniMessage().serialize(getMessage());
            TagResolver resolver = TagResolver.builder()
                    .resolver(TagResolver.standard())
                    .resolver(MiniPlaceholders.getAudiencePlaceholders(audience))
                    .resolver(MiniPlaceholders.getGlobalPlaceholders())
                    .build();
            this.message = getMiniMessage().deserialize(stringMessage, resolver);
        }
        return this;
    }

    public ComponentMessage parsePlaceholderAPI(@Nullable OfflinePlayer player) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            String stringMessage = getMiniMessage().serialize(this.message);
            Matcher matcher = PlaceholderAPI.getPlaceholderPattern().matcher(stringMessage);
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
                stringMessage = stringMessage.replaceAll(safeMatched, parsed);
            }
            this.message = getMiniMessage().deserialize(stringMessage);
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

    @Override
    public int getLength() {
        return toPlainText().length();
    }

    public @NotNull MessageType getMessageType() {
        return this.messageType;
    }

    public ComponentMessage setMessageType(@NotNull MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public StringMessage toStringMessage() {
        String string = getMiniMessage().serialize(this.getMessage());
        return StringMessage.of(string).setMessageType(messageType);
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

}

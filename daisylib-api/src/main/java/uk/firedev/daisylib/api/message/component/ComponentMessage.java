package uk.firedev.daisylib.api.message.component;

import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.message.Message;
import uk.firedev.daisylib.api.message.string.StringMessage;

import java.util.List;

public class ComponentMessage implements Message {

    private @NotNull Component message;
    private static MiniMessage miniMessage = null;
    private static JSONComponentSerializer jsonComponentSerializer = null;

    private ComponentMessage(@NotNull Component message) {
        this.message = message;
    }

    public static ComponentMessage of(@NotNull Component message) {
        return new ComponentMessage(message);
    }

    public static ComponentMessage ofOrDefault(@Nullable Component message, @NotNull Component def) {
        return message == null ? of(def) : of(message);
    }

    public static ComponentMessage fromJson(@NotNull String json) {
        return of(getJsonComponentSerializer().deserialize(json));
    }

    public static ComponentMessage fromString(@NotNull String message) {
        return of(getMiniMessage().deserialize(message));
    }

    public static ComponentMessage fromString(@Nullable String message, @NotNull Component def) {
        final Component finalMessage;
        if (message == null) {
            finalMessage = def;
            Loggers.warn(ComponentMessage.class, "Invalid message supplied. Using the default value.");
        } else {
            finalMessage = getMiniMessage().deserialize(message);
        }
        return of(finalMessage);
    }

    public static ComponentMessage fromStringMessage(@NotNull StringMessage stringMessage) {
        return fromString(stringMessage.getMessage());
    }

    public static ComponentMessage fromConfig(@NotNull YamlConfiguration config, @NotNull String path, @NotNull Component def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        return fromConfigString(message, def, path);
    }

    public static ComponentMessage fromConfig(@NotNull YamlConfiguration config, @NotNull String path, @NotNull String def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        return fromConfigString(message, getMiniMessage().deserialize(def), path);
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

    public static ComponentMessage fromConfig(@NotNull FileConfiguration config, @NotNull String path, @NotNull String def) {
        String message;
        if (config.isList(path)) {
            message = String.join("\n", config.getStringList(path));
        } else {
            message = config.getString(path);
        }
        return fromConfigString(message, getMiniMessage().deserialize(def), path);
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
        return applyReplacer(ComponentReplacer.componentReplacer(placeholder, replacement));
    }

    public ComponentMessage replace(@NotNull String placeholder, @NotNull Component replacement) {
        return applyReplacer(ComponentReplacer.componentReplacer(placeholder, replacement));
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
        String string = getMiniMessage().serialize(this.getMessage());
        return StringMessage.of(string);
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
        this.message = toStringMessage().parsePlaceholderAPI(player).toComponentMessage().getMessage();
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

    @Override
    public int getLength() {
        return toPlainText().length();
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

package uk.firedev.daisylib.messages.message;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.utils.MessageUtils;
import uk.firedev.daisylib.messages.ObjectProcessor;
import uk.firedev.daisylib.messages.config.ConfigReader;
import uk.firedev.daisylib.messages.replacer.Replacer;

import java.util.List;
import java.util.Map;

public abstract class ComponentMessage<C, S> {

    public static final Component ROOT = Component.empty()
        .applyFallbackStyle(
            Style.style()
                .color(NamedTextColor.WHITE)
                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                .build()
        );

    protected ComponentMessage() {}

    // Single Messages

    public static @NotNull ComponentSingleMessage componentMessage(@NotNull Object object, @NotNull MessageType messageType) {
        if (object instanceof String string) {
            return componentMessage(MessageUtils.parseString(string), messageType);
        } else if (object instanceof Component component) {
            return new ComponentSingleMessage(component, messageType);
        }
        return componentMessage(
            Component.join(JoinConfiguration.newlines(), ObjectProcessor.process(object)),
            messageType
        );
    }

    public static @NotNull ComponentSingleMessage componentMessage(@NotNull Object object) {
        return componentMessage(object, MessageType.CHAT);
    }

    // List Messages

    public static @NotNull ComponentListMessage componentMessage(@NotNull List<?> message, @NotNull MessageType messageType) {
        return new ComponentListMessage(
            message.stream()
                .flatMap(object -> ObjectProcessor.process(object).stream())
                .toList(),
            messageType
        );
    }

    public static @NotNull ComponentListMessage componentMessage(@NotNull List<?> message) {
        return componentMessage(message, MessageType.CHAT);
    }

    // Ambiguous Messages - Could be single or list.

    public static @Nullable ComponentMessage<?, ?> componentMessage(@NotNull ConfigReader<?> loader, @NotNull String path) {
        return getFromConfig(loader, path);
    }

    public static @NotNull ComponentMessage<?, ?> componentMessage(@NotNull ConfigReader<?> loader, @NotNull String path, @NotNull String def) {
        ComponentMessage<?, ?> message = getFromConfig(loader, path);
        return message == null ? componentMessage(def) : message;
    }

    public static @NotNull ComponentMessage<?, ?> componentMessage(@NotNull ConfigReader<?> loader, @NotNull String path, @NotNull Component def) {
        ComponentMessage<?, ?> message = getFromConfig(loader, path);
        return message == null ? componentMessage(def) : message;
    }

    // Abstract Things

    /**
     * Turns this ComponentMessage into a ComponentSingleMessage.
     */
    public ComponentSingleMessage toSingleMessage() {
        if (this instanceof ComponentSingleMessage singleMessage) {
            return singleMessage;
        } else if (this instanceof ComponentListMessage listMessage) {
            return new ComponentSingleMessage(
                Component.join(JoinConfiguration.newlines(), listMessage.get()),
                listMessage.messageType()
            );
        } else {
            throw new IllegalArgumentException("Invalid ComponentMessage instance provided.");
        }
    }

    /**
     * Turns this ComponentMessage into a ComponentListMessage.
     */
    public ComponentListMessage toListMessage() {
        if (this instanceof ComponentSingleMessage singleMessage) {
            return new ComponentListMessage(
                List.of(singleMessage.get()),
                singleMessage.messageType()
            );
        } else if (this instanceof ComponentListMessage listMessage) {
            return listMessage;
        } else {
            throw new IllegalArgumentException("Invalid ComponentMessage instance provided.");
        }
    }

    /**
     * Gets the underlying message.
     *
     * @return The underlying message.
     */
    public abstract @NotNull C get();

    /**
     * Gets the underlying message as plain text.
     *
     * @return The underlying message as plain text.
     */
    public abstract @NotNull S getPlainText();

    /**
     * Gets the underlying message as JSON.
     *
     * @return The underlying message as JSON.
     */
    public abstract @NotNull S getJson();

    /**
     * Gets the underlying message as Legacy text.
     *
     * @return The underlying message as Legacy text.
     */
    public abstract @NotNull S getLegacy();

    /**
     * Gets the underlying message as MiniMessage text.
     *
     * @return The underlying message as MiniMessage text.
     */
    public abstract @NotNull S getMiniMessage();

    /**
     * Creates a copy of this ComponentMessage.
     *
     * @return A new ComponentMessage that is a copy of this one.
     */
    public abstract ComponentMessage<?, ?> createCopy();

    /**
     * Gets the MessageType of this message.
     *
     * @return The MessageType of this message.
     */
    public abstract MessageType messageType();

    /**
     * Sets the MessageType of this message.
     *
     * @param messageType The MessageType to set.
     * @return A new ComponentMessage with the updated MessageType.
     */
    public abstract ComponentMessage<?, ?> messageType(@NotNull MessageType messageType);

    /**
     * Appends to the current message.
     *
     * @param append The object to append. Explicitly supports {@link Component} and {@link ComponentMessage}. Anything else will be converted to a String and processed.
     * @return A new ComponentMessage with the appended content.
     */
    public abstract ComponentMessage<?, ?> append(@NotNull Object append);

    /**
     * Prepends to the current message.
     *
     * @param prepend The object to prepend. Explicitly supports {@link Component} and {@link ComponentMessage}. Anything else will be converted to a String and processed.
     * @return A new ComponentMessage with the prepended content.
     */
    public abstract ComponentMessage<?, ?> prepend(@NotNull Object prepend);

    /**
     * Replaces all instances of the specified placeholder with the specified replacement.
     * @param placeholder The placeholder to replace.
     * @param replacement The replacement object. Explicitly supports {@link Component} and {@link ComponentMessage}. Anything else will be converted to a String and processed.
     * @return A new ComponentMessage with the replacements made.
     */
    public abstract ComponentMessage<?, ?> replace(@NotNull String placeholder, @Nullable Object replacement);

    /**
     * Replaces all instances of the specified placeholders with the specified replacements.
     * @param replacements A map of placeholders to replacements. Explicitly supports {@link Component} and {@link ComponentSingleMessage} as values. Anything else will be converted to a String and processed.
     * @return A new ComponentMessage with the replacements made.
     */
    public abstract ComponentMessage<?, ?> replace(@NotNull Map<String, ?> replacements);

    /**
     * Applies the specified Replacer to the message.
     * @param replacer The Replacer to apply.
     * @return A new ComponentMessage with the replacements made.
     */
    public abstract ComponentMessage<?, ?> replace(@Nullable Replacer replacer);

    /**
     * Parses PlaceholderAPI placeholders in the message for the specified player.
     * If PlaceholderAPI is not installed, the message is returned unchanged.
     *
     * @param player The player to parse placeholders for. Can be null for non-player specific placeholders.
     * @return A new ComponentMessage with the parsed placeholders.
     */
    public abstract ComponentMessage<?, ?> parsePlaceholderAPI(@Nullable OfflinePlayer player);

    /**
     * Checks if the underlying plain text is empty.
     * @return True if the underlying plain text is empty, false otherwise.
     */
    public abstract boolean isEmpty();

    /**
     * Gets the length of the underlying plain text.
     * @return The length of the underlying plain text.
     */
    public abstract int getLength();

    /**
     * Sends the message to the specified Audience.
     *
     * @param audience The Audience to send the message to. If null, nothing happens.
     */
    public abstract void send(@Nullable Audience audience);

    /**
     * Sends the message to a list of Audiences.
     *
     * @param audienceList The list of Audiences to send the message to. If the list is empty, nothing happens.
     */
    public abstract void send(@NotNull List<? extends Audience> audienceList);

    /**
     * Sends the message to an array of Audiences.
     *
     * @param audiences The array of Audiences to send the message to. If the array is empty or null, nothing happens.
     */
    public abstract void send(@Nullable Audience... audiences);

    /**
     * Broadcasts the message to all players on the server.
     */
    public abstract void broadcast();

    // Constructor Utils

    private static @Nullable ComponentMessage<?, ?> getFromConfig(@NotNull ConfigReader<?> loader, @NotNull String path) {
        ConfigReader<?> section = loader.getSection(path);
        if (section == null) {
            return fromObject(loader.getObject(path));
        }
        String messageType = section.getString("type");
        MessageType type = MessageType.getFromString(messageType);

        ComponentMessage<?, ?> finalMessage = fromObject(section.getObject("message"));
        return finalMessage != null ? finalMessage.messageType(type) : null;
    }

    private static @Nullable ComponentMessage<?, ?> fromObject(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof List<?> list) {
            return ComponentMessage.componentMessage(list);
        }
        return ComponentMessage.componentMessage(object.toString());
    }

}

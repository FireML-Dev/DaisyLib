package uk.firedev.daisylib.messages.message;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.config.serializer.SoundSerializer;
import uk.firedev.daisylib.messages.ObjectProcessor;
import uk.firedev.daisylib.messages.config.ConfigReader;
import uk.firedev.daisylib.messages.replacer.Replacer;
import uk.firedev.daisylib.utils.CommonUtils;
import uk.firedev.daisylib.utils.MessageUtils;

import java.util.List;
import java.util.Map;

public abstract class Message<C, S> {

    public static final Component ROOT = Component.empty()
        .applyFallbackStyle(
            Style.style()
                .color(NamedTextColor.WHITE)
                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                .build()
        );

    protected Message() {}

    // Single Messages

    public static @NonNull SingleMessage message(@NonNull Object object, @NonNull MessageType messageType) {
        if (object instanceof String string) {
            return message(MessageUtils.parseString(string), messageType);
        } else if (object instanceof Component component) {
            return new SingleMessage(component, messageType, null);
        }
        return message(
            Component.join(JoinConfiguration.newlines(), ObjectProcessor.process(object)),
            messageType
        );
    }

    public static @NonNull SingleMessage message(@NonNull Object object) {
        return message(object, MessageType.CHAT);
    }

    // List Messages

    public static @NonNull ListMessage message(@NonNull List<?> message, @NonNull MessageType messageType) {
        return new ListMessage(
            message.stream()
                .flatMap(object -> ObjectProcessor.process(object).stream())
                .toList(),
            messageType,
            null
        );
    }

    public static @NonNull ListMessage message(@NonNull List<?> message) {
        return message(message, MessageType.CHAT);
    }

    // Ambiguous Messages - Could be single or list.

    public static @Nullable Message<?, ?> message(@NonNull ConfigReader<?> loader, @NonNull String path) {
        return getFromConfig(loader, path);
    }

    public static @NonNull Message<?, ?> message(@NonNull ConfigReader<?> loader, @NonNull String path, @NonNull String def) {
        Message<?, ?> message = getFromConfig(loader, path);
        return message == null ? message(def) : message;
    }

    public static @NonNull Message<?, ?> message(@NonNull ConfigReader<?> loader, @NonNull String path, @NonNull Component def) {
        Message<?, ?> message = getFromConfig(loader, path);
        return message == null ? message(def) : message;
    }

    // Abstract Things

    /**
     * Turns this Message into a ComponentSingleMessage.
     */
    public SingleMessage toSingleMessage() {
        if (this instanceof SingleMessage singleMessage) {
            return singleMessage;
        } else if (this instanceof ListMessage listMessage) {
            return new SingleMessage(
                Component.join(JoinConfiguration.newlines(), listMessage.get()),
                listMessage.messageType(),
                sound()
            );
        } else {
            throw new IllegalArgumentException("Invalid Message instance provided.");
        }
    }

    /**
     * Turns this Message into a ComponentListMessage.
     */
    public ListMessage toListMessage() {
        if (this instanceof SingleMessage singleMessage) {
            return new ListMessage(
                List.of(singleMessage.get()),
                singleMessage.messageType(),
                sound()
            );
        } else if (this instanceof ListMessage listMessage) {
            return listMessage;
        } else {
            throw new IllegalArgumentException("Invalid Message instance provided.");
        }
    }

    /**
     * Gets the underlying message.
     *
     * @return The underlying message.
     */
    public abstract @NonNull C get();

    /**
     * Gets the underlying message as plain text.
     *
     * @return The underlying message as plain text.
     */
    public abstract @NonNull S getPlainText();

    /**
     * Gets the underlying message as JSON.
     *
     * @return The underlying message as JSON.
     */
    public abstract @NonNull S getJson();

    /**
     * Gets the underlying message as Legacy text.
     *
     * @return The underlying message as Legacy text.
     */
    public abstract @NonNull S getLegacy();

    /**
     * Gets the underlying message as MiniMessage text.
     *
     * @return The underlying message as MiniMessage text.
     */
    public abstract @NonNull S getMiniMessage();

    /**
     * Creates a copy of this Message.
     *
     * @return A new Message that is a copy of this one.
     */
    public abstract Message<?, ?> createCopy();

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
     * @return A new Message with the updated MessageType.
     */
    public abstract Message<?, ?> messageType(@NonNull MessageType messageType);

    /**
     * Gets the sound to be played when the message is sent.
     *
     * @return The sound linked to this message.
     */
    public abstract @Nullable Sound sound();

    /**
     * Sets the sound to be played when the message is sent.
     *
     * @param sound The Sound to set.
     * @return A new Message with the new Sound.
     */
    public abstract Message<?, ?> sound(@Nullable Sound sound);

    /**
     * Appends to the current message.
     *
     * @param append The object to append.
     * @return A new Message with the appended content.
     */
    public abstract Message<?, ?> append(@NonNull Object append);

    /**
     * Prepends to the current message.
     *
     * @param prepend The object to prepend.
     * @return A new Message with the prepended content.
     */
    public abstract Message<?, ?> prepend(@NonNull Object prepend);

    /**
     * Replaces all instances of the specified placeholder with the specified replacement.
     * @param placeholder The placeholder to replace.
     * @param replacement The replacement object.
     * @return A new Message with the replacements made.
     */
    public abstract Message<?, ?> replace(@NonNull String placeholder, @Nullable Object replacement);

    public abstract Message<?, ?> replacePlainText(@NonNull String placeholder, @Nullable Object replacement);

    /**
     * Replaces all instances of the specified placeholders with the specified replacements.
     * @param replacements A map of placeholders to replacements.
     * @return A new Message with the replacements made.
     */
    public abstract Message<?, ?> replace(@NonNull Map<String, ?> replacements);

    public abstract Message<?, ?> replacePlainText(@NonNull Map<String, ?> replacements);

    /**
     * Applies the specified Replacer to the message.
     * @param replacer The Replacer to apply.
     * @return A new Message with the replacements made.
     */
    public abstract Message<?, ?> replace(@Nullable Replacer replacer);

    /**
     * Parses PlaceholderAPI placeholders in the message for the specified player.
     * If PlaceholderAPI is not installed, the message is returned unchanged.
     *
     * @param player The player to parse placeholders for. Can be null for non-player specific placeholders.
     * @return A new Message with the parsed placeholders.
     */
    public abstract Message<?, ?> parsePlaceholderAPI(@Nullable OfflinePlayer player);

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
    public abstract void send(@NonNull List<? extends Audience> audienceList);

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

    private static @Nullable Message<?, ?> getFromConfig(@NonNull ConfigReader<?> loader, @NonNull String path) {
        ConfigReader<?> section = loader.getSection(path);
        if (section == null) {
            return fromObject(loader.getObject(path));
        }
        MessageType type = CommonUtils.getEnumValue(MessageType.class, section.getString("type"), MessageType.CHAT);
        Sound sound = SoundSerializer.get().deserialize(section.getString("sound"));

        Message<?, ?> finalMessage = fromObject(section.getObject("message"));
        return finalMessage != null ? finalMessage.messageType(type).sound(sound) : null;
    }

    private static @Nullable Message<?, ?> fromObject(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof List<?> list) {
            return Message.message(list);
        }
        return Message.message(object.toString());
    }

    protected void playSound(@Nullable Audience audience) {
        Sound sound = sound();
        if (audience != null && sound != null) {
            audience.playSound(sound, Sound.Emitter.self());
        }
    }

}

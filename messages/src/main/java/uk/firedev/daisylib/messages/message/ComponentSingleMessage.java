package uk.firedev.daisylib.messages.message;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.DaisyLib;
import uk.firedev.daisylib.common.utils.MessageUtils;
import uk.firedev.daisylib.messages.MessageSettings;
import uk.firedev.daisylib.messages.ObjectProcessor;
import uk.firedev.daisylib.messages.replacer.Replacer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

// NEEDS TO BE IMMUTABLE - any change makes a new instance.
public class ComponentSingleMessage extends ComponentMessage<Component, String> {

    private final Component message;
    private final MessageType messageType;

    protected ComponentSingleMessage(@NonNull Component message, @NonNull MessageType messageType) {
        this.message = ComponentMessage.ROOT.append(message).compact();
        this.messageType = messageType;
    }

    // Message Getters

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Component get() {
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull String getPlainText() {
        return PlainTextComponentSerializer.plainText().serialize(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull String getJson() {
        return GsonComponentSerializer.gson().serialize(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull String getLegacy() {
        return LegacyComponentSerializer.legacySection().serialize(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull String getMiniMessage() {
        return MessageSettings.get().getMiniMessage().serialize(message);
    }

    // Class Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentSingleMessage createCopy() {
        return new ComponentSingleMessage(this.message, messageType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull MessageType messageType() {
        return messageType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentSingleMessage messageType(@NonNull MessageType messageType) {
        return new ComponentSingleMessage(message, messageType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentSingleMessage append(@NonNull Object append) {
        if (!MessageSettings.get().isAllowEmptyAppend() && isEmpty()) {
            DaisyLib.get().getLogging().debug("Cannot append to empty ComponentSingleMessage");
            return this;
        }
        return new ComponentSingleMessage(
            message.append(Component.join(JoinConfiguration.newlines(), ObjectProcessor.process(append))),
            messageType
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentSingleMessage prepend(@NonNull Object prepend) {
        if (!MessageSettings.get().isAllowEmptyPrepend() && isEmpty()) {
            DaisyLib.get().getLogging().debug("Cannot prepend to empty ComponentSingleMessage");
            return this;
        }
        Component processed = Component.join(JoinConfiguration.newlines(),ObjectProcessor.process(prepend));
        if (MessageUtils.isEmpty(processed)) {
            return this;
        }
        return new ComponentSingleMessage(processed, messageType)
            .append(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentSingleMessage replace(@NonNull String placeholder, @Nullable Object replacement) {
        Replacer replacer = Replacer.replacer().addReplacement(placeholder, replacement);
        return new ComponentSingleMessage(replacer.apply(message), messageType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentSingleMessage replace(@NonNull Map<String, ?> replacements) {
        Replacer replacer = Replacer.replacer().addReplacements(replacements);
        return new ComponentSingleMessage(replacer.apply(message), messageType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentSingleMessage replace(@Nullable Replacer replacer) {
        if (replacer == null) {
            return this;
        }
        return new ComponentSingleMessage(replacer.apply(message), messageType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentSingleMessage parsePlaceholderAPI(@Nullable OfflinePlayer player) {
        if (!MessageUtils.PAPI_AVAILABLE) {
            return this;
        }
        return new ComponentSingleMessage(
            MessageUtils.parsePlaceholderAPI(message, player),
            messageType
        );
    }

    /**
     * Checks if the underlying plain text matches the specified string.
     * @param string The string to check against.
     * @return True if the underlying plain text matches the specified string, false otherwise.
     */
    public boolean matches(@NonNull String string) {
        return getPlainText().equals(string);
    }

    /**
     * Checks if the underlying plain text contains the specified string.
     * @param string The strings to check for.
     * @return True if the underlying plain text contains the specified string, false otherwise.
     */
    public boolean contains(@NonNull String @NonNull... string) {
        String plainText = getPlainText();
        return Arrays.stream(string).allMatch(plainText::contains);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return getPlainText().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {
        return getPlainText().length();
    }

    /**
     * Edits the underlying component using the provided editor function.
     * The editor function takes the current component as input and returns a modified component.
     *
     * @param editor A function that modifies the current component.
     * @return A new ComponentSingleMessage with the edited component.
     */
    public ComponentSingleMessage edit(@NonNull Function<Component, Component> editor) {
        return new ComponentSingleMessage(editor.apply(this.message), this.messageType);
    }

    // Sending

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(@Nullable Audience audience) {
        if (isEmpty()) {
            return;
        }
        messageType.send(audience, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(@NonNull List<? extends Audience> audienceList) {
        if (isEmpty()) {
            return;
        }
        audienceList.forEach(this::send);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(@Nullable Audience... audiences) {
        if (isEmpty()) {
            return;
        }
        for (Audience audience : audiences) {
            send(audience);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void broadcast() {
        if (isEmpty()) {
            return;
        }
        Bukkit.broadcast(message);
    }

}

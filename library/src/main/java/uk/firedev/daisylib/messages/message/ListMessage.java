package uk.firedev.daisylib.messages.message;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.messages.MessageSettings;
import uk.firedev.daisylib.messages.ObjectProcessor;
import uk.firedev.daisylib.messages.replacer.Replacer;
import uk.firedev.daisylib.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

// NEEDS TO BE IMMUTABLE - any change makes a new instance.
public class ListMessage extends Message<List<Component>, List<String>> {

    private final List<Component> message = new ArrayList<>();
    private final MessageType messageType;
    private final @Nullable Sound sound;

    protected ListMessage(@NonNull List<Component> message, @NonNull MessageType messageType, @Nullable Sound sound) {
        message.forEach(component -> this.message.add(ROOT.append(component).compact()));
        this.messageType = messageType;
        this.sound = sound;
    }

    // Message Getters

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<Component> get() {
        return new ArrayList<>(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<String> getPlainText() {
        return message.stream()
            .map(component -> PlainTextComponentSerializer.plainText().serialize(component))
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<String> getJson() {
        return message.stream()
            .map(component -> GsonComponentSerializer.gson().serialize(component))
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<String> getLegacy() {
        return message.stream()
            .map(component -> LegacyComponentSerializer.legacySection().serialize(component))
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull List<String> getMiniMessage() {
        MiniMessage mm = MessageSettings.getMiniMessage();
        return message.stream()
            .map(mm::serialize)
            .toList();
    }

    // Class Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMessage createCopy() {
        return new ListMessage(
            List.copyOf(this.message),
            messageType,
            sound
        );
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
    public ListMessage messageType(@NonNull MessageType messageType) {
        return new ListMessage(message, messageType, sound);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Sound sound() {
        return this.sound;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMessage sound(@Nullable Sound sound) {
        return new ListMessage(message, messageType, sound);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMessage append(@NonNull Object append) {
        if (!MessageSettings.isAllowEmptyAppend() && isEmpty()) {
            DaisyLib.get().getLogging().debug("Cannot append to empty ListMessage");
            return this;
        }
        List<Component> newMessage = new ArrayList<>(message);
        newMessage.addAll(
            ObjectProcessor.process(append).stream()
                .map(ROOT::append)
                .toList()
        );
        return new ListMessage(newMessage, messageType, sound);
    }

    /**
     * Appends to each line of the current message.
     *
     * @param append The object to append. Explicitly supports {@link Component} and {@link Message}. Anything else will be converted to a String and processed.
     * @return A new Message with the content appended to each line.
     */
    public ListMessage appendEachLine(@NonNull Object append) {
        if (!MessageSettings.isAllowEmptyAppend() && isEmpty()) {
            DaisyLib.get().getLogging().debug("Cannot append to empty ListMessage");
            return this;
        }
        Component resolved = Component.join(JoinConfiguration.newlines(), ObjectProcessor.process(append));
        List<Component> newMessage = this.message.stream()
            .map(line -> new SingleMessage(line, messageType, sound).append(resolved).get())
            .toList();
        return new ListMessage(newMessage, messageType, sound);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMessage prepend(@NonNull Object prepend) {
        if (!MessageSettings.isAllowEmptyPrepend() && isEmpty()) {
            DaisyLib.get().getLogging().debug("Cannot prepend to empty ListMessage");
            return this;
        }
        List<Component> newMessage = new ArrayList<>(
            ObjectProcessor.process(prepend).stream()
                .map(ROOT::append)
                .toList()
        );
        newMessage.addAll(message);
        return new ListMessage(newMessage, messageType, sound);
    }

    /**
     * Prepends to each line of the current message.
     *
     * @param prepend The object to prepend. Explicitly supports {@link Component} and {@link Message}. Anything else will be converted to a String and processed.
     * @return A new Message with the content prepended to each line.
     */
    public ListMessage prependEachLine(@NonNull Object prepend) {
        if (!MessageSettings.isAllowEmptyPrepend() && isEmpty()) {
            DaisyLib.get().getLogging().debug("Cannot prepend to empty ListMessage");
            return this;
        }
        Component resolved = Component.join(JoinConfiguration.newlines(), ObjectProcessor.process(prepend));
        List<Component> newMessage = this.message.stream()
            .map(line -> new SingleMessage(line, messageType, sound).prepend(resolved).get())
            .toList();
        return new ListMessage(newMessage, messageType, sound);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMessage replace(@NonNull String placeholder, @Nullable Object replacement) {
        Replacer replacer = Replacer.component().addReplacement(placeholder, replacement);
        return new ListMessage(replacer.apply(message), messageType, sound);
    }

    @Override
    public ListMessage replacePlainText(@NonNull String placeholder, @Nullable Object replacement) {
        Replacer replacer = Replacer.plainText().addReplacement(placeholder, replacement);
        return new ListMessage(replacer.apply(message), messageType, sound);
    }

    /**
     * Replaces all instances of the specified placeholder with the specified replacement
     * <p>
     * If a placeholder is found on any line, the replacement list will be inserted in its place.
     * @param placeholder The placeholder to replace.
     * @param replacement The replacement object. Explicitly supports {@link Component} and {@link Message}. Anything else will be converted to a String and processed.
     * @return A new Message with the replacements made.
     */
    public ListMessage replaceWithListInsertion(@NonNull String placeholder, @Nullable Object replacement) {
        Replacer replacer = Replacer.component().addReplacement(placeholder, replacement);
        return new ListMessage(replacer.applyWithListInsertion(message), messageType, sound);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMessage replace(@NonNull Map<String, ?> replacements) {
        Replacer replacer = Replacer.component().addReplacements(replacements);
        return new ListMessage(replacer.apply(message), messageType, sound);
    }

    @Override
    public ListMessage replacePlainText(@NonNull Map<String, ?> replacements) {
        Replacer replacer = Replacer.plainText().addReplacements(replacements);
        return new ListMessage(replacer.apply(message), messageType, sound);
    }

    /**
     * Replaces all instances of the specified placeholders with the specified replacements.
     * <p>
     * If a placeholder is found on any line, the replacement list will be inserted in its place.
     * @param replacements A map of placeholders to replacements. Explicitly supports {@link Component} and {@link SingleMessage} as values. Anything else will be converted to a String and processed.
     * @return A new Message with the replacements made.
     */
    public ListMessage replaceWithListInsertion(@NonNull Map<String, ?> replacements) {
        Replacer replacer = Replacer.component().addReplacements(replacements);
        return new ListMessage(replacer.applyWithListInsertion(message), messageType, sound);
    }

    public ListMessage replaceWithListInsertionPlainText(@NonNull Map<String, ?> replacements) {
        Replacer replacer = Replacer.plainText().addReplacements(replacements);
        return new ListMessage(replacer.applyWithListInsertion(message), messageType, sound);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMessage replace(@Nullable Replacer replacer) {
        if (replacer == null) {
            return this;
        }
        return new ListMessage(replacer.apply(message), messageType, sound);
    }

    /**
     * Applies the specified Replacer to the message.
     * <p>
     * If a placeholder is found on any line, the replacement list will be inserted in its place.
     * @param replacer The Replacer to apply.
     * @return A new Message with the replacements made.
     */
    public ListMessage replaceWithListInsertion(@Nullable Replacer replacer) {
        if (replacer == null) {
            return this;
        }
        return new ListMessage(replacer.applyWithListInsertion(message), messageType, sound);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMessage parsePlaceholderAPI(@Nullable OfflinePlayer player) {
        if (!MessageUtils.PAPI_AVAILABLE) {
            return this;
        }

        return new ListMessage(
            message.stream()
                .map(component -> MessageUtils.parsePlaceholderAPI(component, player))
                .toList(),
            messageType,
            sound
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return message.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {
        return message.size();
    }

    /**
     * Converts this ListMessage into a list of SingleMessages.
     *
     * @return A list of ComponentSingleMessages representing each line of the original message.
     */
    public List<SingleMessage> toSingleMessages() {
        return message.stream()
            .map(line -> new SingleMessage(line, this.messageType, sound))
            .toList();
    }

    /**
     * Edits the current message using the provided editor function.
     *
     * @param editor A function that takes a List of Components and returns a modified List of Components.
     * @return A new ListMessage with the edited message.
     */
    public ListMessage edit(@NonNull Function<List<Component>, List<Component>> editor) {
        List<Component> newMessage = editor.apply(new ArrayList<>(this.message));
        return new ListMessage(newMessage, this.messageType, sound);
    }

    /**
     * Edits each line of the current message using the provided editor function.
     *
     * @param editor A function that takes a ComponentSingleMessage and returns a modified ComponentSingleMessage.
     * @return A new ListMessage with each line edited.
     */
    public ListMessage editAllLines(@NonNull Function<SingleMessage, SingleMessage> editor) {
        List<Component> newMessage = message.stream()
            .map(line -> editor.apply(new SingleMessage(line, this.messageType, sound)))
            .filter(Objects::nonNull)
            .map(SingleMessage::get)
            .toList();
        return new ListMessage(newMessage, this.messageType, sound);
    }

    // Sending

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(@Nullable Audience audience) {
        if (isEmpty() || audience == null) {
            return;
        }
        if (message.size() >= 2 && messageType == MessageType.TITLE) {
            Component main = message.get(0);
            Component sub = message.get(1);
            Title title = Title.title(main, sub);
            audience.showTitle(title);
            playSound(audience);
            return;
        }
        message.forEach(component -> messageType.send(audience, component));
        playSound(audience);
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
        message.forEach(Bukkit::broadcast);
        if (sound != null) {
            Bukkit.getOnlinePlayers().forEach(this::playSound);
        }
    }

}

package uk.firedev.daisylib.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;

import java.util.regex.Matcher;

public class MessageUtils {

    private static final char SECTION = '§';
    public static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.builder()
        .character('&')
        .hexColors()
        .useUnusualXRepeatedCharacterHexFormat()
        .build();
    public static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER_SECTION = LegacyComponentSerializer.builder()
        .character(SECTION)
        .hexColors()
        .useUnusualXRepeatedCharacterHexFormat()
        .build();
    public static final boolean PAPI_AVAILABLE = CommonUtils.classExists("me.clip.placeholderapi.PlaceholderAPI");

    private MessageUtils() {}

    /**
     * Checks if the given String contains legacy colors.
     * <p>
     * Always false unless {@link uk.firedev.daisylib.DaisyLib.Settings#ALLOW_LEGACY_MESSAGES} is true.
     */
    public static boolean containsLegacy(@NonNull String message) {
        if (message.isEmpty()) {
            return false;
        }
        if (!DaisyLib.Settings.ALLOW_LEGACY_MESSAGES.get()) {
            return false;
        }
        // If the message contains a section sign, it's definitely legacy.
        if (message.contains(Character.toString(SECTION))) {
            return true;
        }
        // If no MiniMessage tags get stripped, the message is assumed to be legacy.
        return MiniMessage.miniMessage().stripTags(message).equals(message);
    }

    /**
     * Processes a String into a Component, detecting whether it's Legacy or MiniMessage format.
     * @param message The message to process.
     * @return The processed Component.
     */
    public static @NonNull Component parseString(@NonNull String message) {
        return parseString(message, MiniMessage.miniMessage());
    }

    /**
     * Processes a String into a Component, detecting whether it's Legacy or MiniMessage format. A custom MiniMessage instance can be passed.
     * @param message The message to process.
     * @param miniMessage The MiniMessage instance to use.
     * @return The processed Component.
     */
    public static @NonNull Component parseString(@NonNull String message, @NonNull MiniMessage miniMessage) {
        if (message.isEmpty()) {
            return Component.empty();
        }
        if (containsLegacy(message)) {
            // Choose the correct serializer
            LegacyComponentSerializer serializer = message.contains(Character.toString(SECTION))
                ? LEGACY_COMPONENT_SERIALIZER_SECTION
                : LEGACY_COMPONENT_SERIALIZER;
            return serializer.deserialize(message);
        } else {
            return miniMessage.deserialize(message);
        }
    }

    public static boolean isEmpty(@NonNull Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component).isEmpty();
    }

    public static Component parsePlaceholderAPI(@NonNull Component component, @Nullable OfflinePlayer player) {
        if (!PAPI_AVAILABLE) {
            DaisyLib.get().getLogging().debug("PlaceholderAPI not found. It's either not installed or not a dependency.");
            return component;
        }
        MiniMessage mm = MiniMessage.miniMessage();
        String input = mm.serialize(component);

        if (!input.contains("%")) {
            return component;
        }

        Matcher matcher = PlaceholderAPI.getPlaceholderPattern().matcher(input);
        String result = matcher.replaceAll("<papi:$1>");

        return mm.deserialize(result, getPapiResolver(player));
    }

    private static @NonNull TagResolver getPapiResolver(@Nullable OfflinePlayer player) {
        return TagResolver.resolver("papi", (argumentQueue, context) -> {
            // Get the string placeholder that they want to use.
            final String papiPlaceholder = argumentQueue.popOr("papi tag requires an argument").value();

            // Then get PAPI to parse the placeholder for the given player.
            final String parsedPlaceholder = PlaceholderAPI.setPlaceholders(player, '%' + papiPlaceholder + '%');

            // We need to turn this ugly legacy string into a nice component.
            final Component componentPlaceholder = MessageUtils.LEGACY_COMPONENT_SERIALIZER_SECTION.deserialize(parsedPlaceholder);

            // Finally, return the tag instance to insert the placeholder!
            return Tag.selfClosingInserting(componentPlaceholder);
        });
    }

}

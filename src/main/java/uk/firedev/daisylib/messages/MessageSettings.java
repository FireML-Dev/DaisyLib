package uk.firedev.daisylib.messages;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jspecify.annotations.NonNull;

import java.util.function.Function;

public class MessageSettings {

    private static MiniMessage.@NonNull Builder miniMessageBuilder = MiniMessage.builder().postProcessor(component -> component);
    private static @NonNull MiniMessage miniMessage = miniMessageBuilder.build();
    private static boolean enableLegacy = false;
    private static boolean allowEmptyAppend = false;
    private static boolean allowEmptyPrepend = false;
    private static boolean allowDebug = false;

    private MessageSettings() {}

    public static boolean isEnableLegacy() {
        return enableLegacy;
    }

    public static void setEnableLegacy(boolean allow) {
        enableLegacy = allow;
    }

    public static boolean isAllowEmptyAppend() {
        return allowEmptyAppend;
    }

    public static void setAllowEmptyAppend(boolean allow) {
        allowEmptyAppend = allow;
    }

    public static boolean isAllowEmptyPrepend() {
        return allowEmptyPrepend;
    }

    public static void setAllowEmptyPrepend(boolean allow) {
        allowEmptyPrepend = allow;
    }

    public static boolean isAllowDebug() {
        return allowDebug;
    }

    public static void setAllowDebug(boolean allow) {
        allowDebug = allow;
    }

    public static @NonNull MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public static void editMiniMessage(@NonNull Function<MiniMessage.@NonNull Builder, MiniMessage.@NonNull Builder> editor) {
        miniMessageBuilder = editor.apply(miniMessageBuilder);
        miniMessage = miniMessageBuilder.build();
    }

}

package uk.firedev.daisylib.messages;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class MessageSettings {

    private static final MessageSettings instance = new MessageSettings();

    private @NotNull MiniMessage.Builder miniMessageBuilder = MiniMessage.builder()
        .postProcessor(component -> component);
    private @NotNull MiniMessage miniMessage = miniMessageBuilder.build();
    private boolean enableLegacy = false;
    private boolean allowEmptyAppend = false;
    private boolean allowEmptyPrepend = false;
    private boolean allowDebug = false;

    private MessageSettings() {}

    public static @NotNull MessageSettings get() {
        return instance;
    }

    public boolean isEnableLegacy() {
        return this.enableLegacy;
    }

    public void setEnableLegacy(boolean allow) {
        this.enableLegacy = allow;
    }

    public boolean isAllowEmptyAppend() {
        return this.allowEmptyAppend;
    }

    public void setAllowEmptyAppend(boolean allowEmptyAppend) {
        this.allowEmptyAppend = allowEmptyAppend;
    }

    public boolean isAllowEmptyPrepend() {
        return this.allowEmptyPrepend;
    }

    public void setAllowEmptyPrepend(boolean allowEmptyPrepend) {
        this.allowEmptyPrepend = allowEmptyPrepend;
    }

    public boolean isAllowDebug() {
        return this.allowDebug;
    }

    public void setAllowDebug(boolean allowDebug) {
        this.allowDebug = allowDebug;
    }

    public @NotNull MiniMessage getMiniMessage() {
        return this.miniMessage;
    }

    public void editMiniMessage(@NotNull Function<MiniMessage.@NotNull Builder, MiniMessage.@NotNull Builder> editor) {
        this.miniMessageBuilder = editor.apply(this.miniMessageBuilder);
        this.miniMessage = this.miniMessageBuilder.build();
    }

}

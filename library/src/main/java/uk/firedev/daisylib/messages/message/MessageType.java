package uk.firedev.daisylib.messages.message;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.BiConsumer;

public enum MessageType {
    CHAT(Audience::sendMessage),
    ACTION_BAR(Audience::sendActionBar),
    TITLE((audience, component) -> {
        Title title = Title.title(component, Component.empty());
        audience.showTitle(title);
    }),
    SUBTITLE((audience, component) -> {
        Title title = Title.title(Component.empty(), component);
        audience.showTitle(title);
    });

    private final BiConsumer<Audience, Component> consumer;

    MessageType(@NonNull BiConsumer<Audience, Component> consumer) {
        this.consumer = consumer;
    }

    public void send(@Nullable Audience audience, @NonNull Component message) {
        if (audience == null) {
            return;
        }
        consumer.accept(audience, message);
    }

}

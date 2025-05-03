package uk.firedev.daisylib.api.message;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Message {

    void sendMessage(@Nullable Audience audience);

    default void sendMessage(@NotNull List<Audience> audienceList) {
        audienceList.forEach(this::sendMessage);
    }

    void broadcast();

    int getLength();

    enum MessageType {
        CHAT,
        ACTION_BAR,
        TITLE,
        SUBTITLE
    }

}

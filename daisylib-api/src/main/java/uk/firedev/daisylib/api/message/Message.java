package uk.firedev.daisylib.api.message;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Message {

    void sendMessage(@Nullable Audience audience);

    void sendMessage(@NotNull List<Audience> audienceList);

    void sendActionBar(@Nullable Audience audience);

    void broadcast();

    int getLength();

}

package uk.firedev.daisylib.message;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Message {

    void sendMessage(@NotNull Audience audience);

    void sendMessage(@NotNull List<Audience> audienceList);

    void sendActionBar(@NotNull Audience audience);

    void broadcast();

}

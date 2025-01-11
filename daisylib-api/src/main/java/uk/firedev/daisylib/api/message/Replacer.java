package uk.firedev.daisylib.api.message;

import org.jetbrains.annotations.NotNull;

public interface Replacer {

    Replacer prefix(@NotNull String prefix);

    String prefix();

    Replacer suffix(@NotNull String suffix);

    String suffix();

}

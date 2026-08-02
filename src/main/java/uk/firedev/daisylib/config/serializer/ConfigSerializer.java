package uk.firedev.daisylib.config.serializer;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface ConfigSerializer<T> {

    @NonNull String serialize(@NonNull T element);

    @Nullable T deserialize(@Nullable String element);

}

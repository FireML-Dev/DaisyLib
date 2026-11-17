package uk.firedev.daisylib.messages.config;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ConfigReader<T> {

    @Nullable Object getObject(String path);

    @Nullable String getString(String path);

    @NonNull List<String> getStringList(String path);

    @NonNull T getConfig();

    @Nullable ConfigReader<T> getSection(@NonNull String path);

}

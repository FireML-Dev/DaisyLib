package uk.firedev.daisylib.messages.config;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ConfigReader<T> {

    @Nullable Object getObject(String path);

    @Nullable String getString(String path);

    @NotNull List<String> getStringList(String path);

    @NotNull T getConfig();

    @Nullable ConfigReader<T> getSection(@NotNull String path);

}

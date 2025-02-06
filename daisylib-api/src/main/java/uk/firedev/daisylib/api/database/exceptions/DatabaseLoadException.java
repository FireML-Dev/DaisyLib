package uk.firedev.daisylib.api.database.exceptions;

import org.jetbrains.annotations.NotNull;

public class DatabaseLoadException extends Exception {

    public DatabaseLoadException(@NotNull String message) {
        super(message);
    }

    public DatabaseLoadException(@NotNull Throwable throwable) {
        super(throwable);
    }

}

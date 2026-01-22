package uk.firedev.daisylib.database.exceptions;

import org.jspecify.annotations.NonNull;

public class DatabaseLoadException extends Exception {

    public DatabaseLoadException(@NonNull String message) {
        super(message);
    }

    public DatabaseLoadException(@NonNull Throwable throwable) {
        super(throwable);
    }

}

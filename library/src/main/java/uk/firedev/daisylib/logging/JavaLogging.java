package uk.firedev.daisylib.logging;

import org.jspecify.annotations.NonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaLogging implements Logging {

    private final Logger logger;

    protected JavaLogging(@NonNull Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(@NonNull String message) {
        logger.info(message);
    }

    @Override
    public void info(@NonNull String message, @NonNull Throwable throwable) {
        logger.log(Level.INFO, message, throwable);
    }

    @Override
    public void warn(@NonNull String message) {
        logger.warning(message);
    }

    @Override
    public void warn(@NonNull String message, @NonNull Throwable throwable) {
        logger.log(Level.WARNING, message, throwable);
    }

    @Override
    public void error(@NonNull String message) {
        logger.severe(message);
    }

    @Override
    public void error(@NonNull String message, @NonNull Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

}

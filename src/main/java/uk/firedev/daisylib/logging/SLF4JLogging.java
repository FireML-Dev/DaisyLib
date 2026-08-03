package uk.firedev.daisylib.logging;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

public class SLF4JLogging implements Logging {

    private final Logger logger;

    protected SLF4JLogging(@NonNull Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(@NonNull String message) {
        logger.info(message);
    }

    @Override
    public void info(@NonNull String message, @NonNull Throwable throwable) {
        logger.info(message, throwable);
    }

    @Override
    public void warn(@NonNull String message) {
        logger.warn(message);
    }

    @Override
    public void warn(@NonNull String message, @NonNull Throwable throwable) {
        logger.warn(message, throwable);
    }

    @Override
    public void error(@NonNull String message) {
        logger.error(message);
    }

    @Override
    public void error(@NonNull String message, @NonNull Throwable throwable) {
        logger.error(message, throwable);
    }

}

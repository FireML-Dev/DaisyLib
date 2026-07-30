package uk.firedev.daisylib.common.logging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.NonNull;

public class ComponentLogging extends SLF4JLogging implements Logging {

    private final ComponentLogger logger;

    protected ComponentLogging(@NonNull ComponentLogger logger) {
        super(logger);
        this.logger = logger;
    }

    public void info(@NonNull Component message) {
        logger.info(message);
    }

    public void info(@NonNull Component message, @NonNull Throwable throwable) {
        logger.info(message, throwable);
    }

    public void warn(@NonNull Component message) {
        logger.warn(message);
    }

    public void warn(@NonNull Component message, @NonNull Throwable throwable) {
        logger.warn(message, throwable);
    }

    public void error(@NonNull Component message) {
        logger.error(message);
    }

    public void error(@NonNull Component message, @NonNull Throwable throwable) {
        logger.error(message, throwable);
    }

}
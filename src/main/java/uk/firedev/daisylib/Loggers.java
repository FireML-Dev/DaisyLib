package uk.firedev.daisylib;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.message.component.ComponentMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Loggers {
    
    // net.kyori.adventure.text.logger.slf4j.ComponentLogger methods
    
    public static void logException(@NotNull ComponentLogger logger, @NotNull Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
    }

    public static void info(@NotNull ComponentLogger logger, @NotNull Component message) {
        logger.info(message);
    }

    public static void info(@NotNull ComponentLogger logger, @NotNull Component message, @NotNull Throwable throwable) {
        info(logger, message);
        logException(logger, throwable);
    }


    public static void info(@NotNull ComponentLogger logger, @NotNull String message) {
        logger.info(ComponentMessage.fromString(message).getMessage());
    }

    public static void info(@NotNull ComponentLogger logger, @NotNull String message, @NotNull Throwable throwable) {
        info(logger, message);
        logException(logger, throwable);
    }

    public static void warn(@NotNull ComponentLogger logger, @NotNull Component message) {
        logger.warn(message);
    }

    public static void warn(@NotNull ComponentLogger logger, @NotNull Component message, @NotNull Throwable throwable) {
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void warn(@NotNull ComponentLogger logger, @NotNull String message) {
        logger.warn(ComponentMessage.fromString(message).getMessage());
    }

    public static void warn(@NotNull ComponentLogger logger, @NotNull String message, @NotNull Throwable throwable) {
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void error(@NotNull ComponentLogger logger, @NotNull Component message) {
        logger.error(message);
    }

    public static void error(@NotNull ComponentLogger logger, @NotNull Component message, @NotNull Throwable throwable) {
        error(logger, message);
        logException(logger, throwable);
    }

    public static void error(@NotNull ComponentLogger logger, @NotNull String message) {
        logger.error(ComponentMessage.fromString(message).getMessage());
    }

    public static void error(@NotNull ComponentLogger logger, @NotNull String message, @NotNull Throwable throwable) {
        error(logger, message);
        logException(logger, throwable);
    }

    // java.util.logging.Logger methods

    public static void logException(@NotNull Logger logger, @NotNull Throwable throwable) {
        logger.log(Level.SEVERE, throwable.getMessage(), throwable);
    }

    public static void info(@NotNull Logger logger, @NotNull String message) {
        logger.info(message);
    }

    public static void info(@NotNull Logger logger, @NotNull String message, @NotNull Throwable throwable) {
        info(logger, message);
        logException(logger, throwable);
    }

    public static void warn(@NotNull Logger logger, @NotNull String message) {
        logger.warning(message);
    }

    public static void warn(@NotNull Logger logger, @NotNull String message, @NotNull Throwable throwable) {
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void error(@NotNull Logger logger, @NotNull String message) {
        logger.severe(message);
    }

    public static void error(@NotNull Logger logger, @NotNull String message, @NotNull Throwable throwable) {
        error(logger, message);
        logException(logger, throwable);
    }

}
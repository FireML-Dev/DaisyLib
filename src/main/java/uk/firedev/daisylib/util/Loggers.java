package uk.firedev.daisylib.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.NonNull;
import org.slf4j.LoggerFactory;
import uk.firedev.messagelib.message.ComponentMessage;

import java.util.logging.Level;

public class Loggers {
    
    // net.kyori.adventure.text.logger.slf4j.ComponentLogger methods
    
    public static void logException(@NonNull ComponentLogger logger, @NonNull Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
    }

    public static void info(@NonNull ComponentLogger logger, @NonNull Component message) {
        logger.info(message);
    }

    public static void info(@NonNull ComponentLogger logger, @NonNull Component message, @NonNull Throwable throwable) {
        info(logger, message);
        logException(logger, throwable);
    }

    public static void info(@NonNull ComponentLogger logger, @NonNull String message) {
        logger.info(ComponentMessage.componentMessage(message).get());
    }

    public static void info(@NonNull ComponentLogger logger, @NonNull String message, @NonNull Throwable throwable) {
        info(logger, message);
        logException(logger, throwable);
    }

    public static void warn(@NonNull ComponentLogger logger, @NonNull Component message) {
        logger.warn(message);
    }

    public static void warn(@NonNull ComponentLogger logger, @NonNull Component message, @NonNull Throwable throwable) {
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void warn(@NonNull ComponentLogger logger, @NonNull String message) {
        logger.warn(ComponentMessage.componentMessage(message).get());
    }

    public static void warn(@NonNull ComponentLogger logger, @NonNull String message, @NonNull Throwable throwable) {
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void error(@NonNull ComponentLogger logger, @NonNull Component message) {
        logger.error(message);
    }

    public static void error(@NonNull ComponentLogger logger, @NonNull Component message, @NonNull Throwable throwable) {
        error(logger, message);
        logException(logger, throwable);
    }

    public static void error(@NonNull ComponentLogger logger, @NonNull String message) {
        logger.error(ComponentMessage.componentMessage(message).get());
    }

    public static void error(@NonNull ComponentLogger logger, @NonNull String message, @NonNull Throwable throwable) {
        error(logger, message);
        logException(logger, throwable);
    }

    // org.slf4j.Logger methods

    public static void logException(org.slf4j.@NonNull Logger logger, @NonNull Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
    }

    public static void logException(@NonNull Class<?> clazz, @NonNull Throwable throwable) {
        logException(LoggerFactory.getLogger(clazz), throwable);
    }

    public static void info(org.slf4j.@NonNull Logger logger, @NonNull String message) {
        logger.info(message);
    }

    public static void info(org.slf4j.@NonNull Logger logger, @NonNull String message, @NonNull Throwable throwable) {
        info(logger, message);
        logException(logger, throwable);
    }

    public static void info(@NonNull Class<?> clazz, @NonNull String message) {
        info(LoggerFactory.getLogger(clazz), message);
    }

    public static void info(@NonNull Class<?> clazz, @NonNull String message, @NonNull Throwable throwable) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
        info(logger, message);
        logException(logger, throwable);
    }

    public static void warn(org.slf4j.@NonNull Logger logger, @NonNull String message) {
        logger.warn(message);
    }

    public static void warn(org.slf4j.@NonNull Logger logger, @NonNull String message, @NonNull Throwable throwable) {
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void warn(@NonNull Class<?> clazz, @NonNull String message) {
        warn(LoggerFactory.getLogger(clazz), message);
    }

    public static void warn(@NonNull Class<?> clazz, @NonNull String message, @NonNull Throwable throwable) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void error(org.slf4j.@NonNull Logger logger, @NonNull String message) {
        logger.error(message);
    }

    public static void error(org.slf4j.@NonNull Logger logger, @NonNull String message, @NonNull Throwable throwable) {
        error(logger, message);
        logException(logger, throwable);
    }

    public static void error(@NonNull Class<?> clazz, @NonNull String message) {
        error(LoggerFactory.getLogger(clazz), message);
    }

    public static void error(@NonNull Class<?> clazz, @NonNull String message, @NonNull Throwable throwable) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
        error(logger, message);
        logException(logger, throwable);
    }

    // java.util.logging.Logger methods

    public static void logException(java.util.logging.@NonNull Logger logger, @NonNull Throwable throwable) {
        logger.log(Level.SEVERE, throwable.getMessage(), throwable);
    }

    public static void info(java.util.logging.@NonNull Logger logger, @NonNull String message) {
        logger.info(message);
    }

    public static void info(java.util.logging.@NonNull Logger logger, @NonNull String message, @NonNull Throwable throwable) {
        info(logger, message);
        logException(logger, throwable);
    }

    public static void warn(java.util.logging.@NonNull Logger logger, @NonNull String message) {
        logger.warning(message);
    }

    public static void warn(java.util.logging.@NonNull Logger logger, @NonNull String message, @NonNull Throwable throwable) {
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void error(java.util.logging.@NonNull Logger logger, @NonNull String message) {
        logger.severe(message);
    }

    public static void error(java.util.logging.@NonNull Logger logger, @NonNull String message, @NonNull Throwable throwable) {
        error(logger, message);
        logException(logger, throwable);
    }

}
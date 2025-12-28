package uk.firedev.daisylib.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import uk.firedev.messagelib.message.ComponentMessage;

import java.util.logging.Level;

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
        logger.info(ComponentMessage.componentMessage(message).get());
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
        logger.warn(ComponentMessage.componentMessage(message).get());
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
        logger.error(ComponentMessage.componentMessage(message).get());
    }

    public static void error(@NotNull ComponentLogger logger, @NotNull String message, @NotNull Throwable throwable) {
        error(logger, message);
        logException(logger, throwable);
    }

    // org.slf4j.Logger methods

    public static void logException(@NotNull org.slf4j.Logger logger, @NotNull Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
    }

    public static void logException(@NotNull Class<?> clazz, @NotNull Throwable throwable) {
        logException(LoggerFactory.getLogger(clazz), throwable);
    }

    public static void info(@NotNull org.slf4j.Logger logger, @NotNull String message) {
        logger.info(message);
    }

    public static void info(@NotNull org.slf4j.Logger logger, @NotNull String message, @NotNull Throwable throwable) {
        info(logger, message);
        logException(logger, throwable);
    }

    public static void info(@NotNull Class<?> clazz, @NotNull String message) {
        info(LoggerFactory.getLogger(clazz), message);
    }

    public static void info(@NotNull Class<?> clazz, @NotNull String message, @NotNull Throwable throwable) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
        info(logger, message);
        logException(logger, throwable);
    }

    public static void warn(@NotNull org.slf4j.Logger logger, @NotNull String message) {
        logger.warn(message);
    }

    public static void warn(@NotNull org.slf4j.Logger logger, @NotNull String message, @NotNull Throwable throwable) {
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void warn(@NotNull Class<?> clazz, @NotNull String message) {
        warn(LoggerFactory.getLogger(clazz), message);
    }

    public static void warn(@NotNull Class<?> clazz, @NotNull String message, @NotNull Throwable throwable) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void error(@NotNull org.slf4j.Logger logger, @NotNull String message) {
        logger.error(message);
    }

    public static void error(@NotNull org.slf4j.Logger logger, @NotNull String message, @NotNull Throwable throwable) {
        error(logger, message);
        logException(logger, throwable);
    }

    public static void error(@NotNull Class<?> clazz, @NotNull String message) {
        error(LoggerFactory.getLogger(clazz), message);
    }

    public static void error(@NotNull Class<?> clazz, @NotNull String message, @NotNull Throwable throwable) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
        error(logger, message);
        logException(logger, throwable);
    }

    // java.util.logging.Logger methods

    public static void logException(@NotNull java.util.logging.Logger logger, @NotNull Throwable throwable) {
        logger.log(Level.SEVERE, throwable.getMessage(), throwable);
    }

    public static void info(@NotNull java.util.logging.Logger logger, @NotNull String message) {
        logger.info(message);
    }

    public static void info(@NotNull java.util.logging.Logger logger, @NotNull String message, @NotNull Throwable throwable) {
        info(logger, message);
        logException(logger, throwable);
    }

    public static void warn(@NotNull java.util.logging.Logger logger, @NotNull String message) {
        logger.warning(message);
    }

    public static void warn(@NotNull java.util.logging.Logger logger, @NotNull String message, @NotNull Throwable throwable) {
        warn(logger, message);
        logException(logger, throwable);
    }

    public static void error(@NotNull java.util.logging.Logger logger, @NotNull String message) {
        logger.severe(message);
    }

    public static void error(@NotNull java.util.logging.Logger logger, @NotNull String message, @NotNull Throwable throwable) {
        error(logger, message);
        logException(logger, throwable);
    }

}
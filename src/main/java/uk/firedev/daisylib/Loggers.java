package uk.firedev.daisylib;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import uk.firedev.daisylib.message.component.ComponentMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Loggers {
    
    // net.kyori.adventure.text.logger.slf4j.ComponentLogger methods
    
    public static void logException(ComponentLogger logger, Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
    }

    public static void info(ComponentLogger logger, Component message) {
        logger.info(message);
    }

    public static void info(ComponentLogger logger, String message) {
        logger.info(new ComponentMessage(message).getMessage());
    }
    
    public static void warn(ComponentLogger logger, Component message) {
        logger.warn(message);
    }

    public static void warn(ComponentLogger logger, String message) {
        logger.warn(new ComponentMessage(message).getMessage());
    }
    
    public static void error(ComponentLogger logger, Component message) {
        logger.error(message);
    }

    public static void error(ComponentLogger logger, String message) {
        logger.error(new ComponentMessage(message).getMessage());
    }
    
    // java.util.logging.Logger methods

    public static void logException(Logger logger, Throwable throwable) {
        logger.log(Level.SEVERE, throwable.getMessage(), throwable);
    }

    public static void info(Logger logger, String message) {
        logger.info(message);
    }

    public static void warn(Logger logger, String message) {
        logger.warning(message);
    }

    public static void error(Logger logger, String message) {
        logger.severe(message);
    }

}
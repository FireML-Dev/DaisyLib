package uk.firedev.daisylib;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This entire class only exists in case I want to do fancy log formatting
 */
public class Loggers {

    public static void logException(Throwable throwable, Logger logger) { logger.log(Level.SEVERE, throwable.getMessage(), throwable); }

    public static void log(Level level, Logger logger, String message) { logger.log(level, message); }

    public static void log(Level level, Logger logger, String message, Throwable thrown) { logger.log(level, message, thrown); }

    public static void info(Logger logger, String message) { logger.log(Level.INFO, message); }

    public static void warning(Logger logger, String message) { logger.log(Level.WARNING, message); }

    public static void severe(Logger logger, String message) { logger.log(Level.SEVERE, message); }

}
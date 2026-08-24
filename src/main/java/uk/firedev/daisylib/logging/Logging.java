package uk.firedev.daisylib.logging;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.firedev.daisylib.DaisyLib;

public interface Logging {

    static JavaLogging logging(java.util.logging.@NonNull Logger logger) {
        return new JavaLogging(logger);
    }

    static SLF4JLogging logging(org.slf4j.@NonNull Logger logger) {
        return new SLF4JLogging(logger);
    }

    static SLF4JLogging logging(@NonNull String name) {
        Logger logger = LoggerFactory.getLogger(name);
        return new SLF4JLogging(logger);
    }

    static ComponentLogging logging(net.kyori.adventure.text.logger.slf4j.@NonNull ComponentLogger logger) {
        return new ComponentLogging(logger);
    }
    
    static SLF4JLogging logging(@NonNull Class<?> clazz) {
        Logger logger = LoggerFactory.getLogger(clazz);
        return new SLF4JLogging(logger);
    }

    static ComponentLogging logging(@NonNull Plugin plugin) {
        return new ComponentLogging(plugin.getComponentLogger());
    }

    void info(@NonNull String message);

    void info(@NonNull String message, @NonNull Throwable throwable);

    default void info(@NonNull String @NonNull ... message) {
        for (String line : message) {
            info(line);
        }
    }

    void warn(@NonNull String message);

    void warn(@NonNull String message, @NonNull Throwable throwable);

    default void warn(@NonNull String @NonNull ... message) {
        for (String line : message) {
            warn(line);
        }
    }

    void error(@NonNull String message);

    void error(@NonNull String message, @NonNull Throwable throwable);

    default void error(@NonNull String @NonNull ... message) {
        for (String line : message) {
            error(line);
        }
    }

    /**
     * Sends a debug message to console.
     * <p>
     * Does nothing unless {@link uk.firedev.daisylib.DaisyLib.Settings#ENABLE_DEBUG} is true.
     */
    default void debug(@NonNull String message) {
        if (DaisyLib.Settings.ENABLE_DEBUG.get()) {
            info(formatDebug(message));
        }
    }

    /**
     * Sends a debug message to console with an exception.
     * <p>
     * Does nothing unless {@link uk.firedev.daisylib.DaisyLib.Settings#ENABLE_DEBUG} is true.
     */
    default void debug(@NonNull String message, @NonNull Throwable throwable) {
        if (DaisyLib.Settings.ENABLE_DEBUG.get()) {
            info(formatDebug(message), throwable);
        }
    }

    /**
     * Sends debug messages to console.
     * <p>
     * Does nothing unless {@link uk.firedev.daisylib.DaisyLib.Settings#ENABLE_DEBUG} is true.
     */
    default void debug(@NonNull String @NonNull ... message) {
        for (String line : message) {
            debug(line);
        }
    }

    default void exception(@NonNull Throwable throwable) {
        error(throwable.getMessage(), throwable);
    }

    default @NonNull String formatDebug(@NonNull String message) {
        return "[DEBUG] " + message;
    }

}

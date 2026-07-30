package uk.firedev.daisylib.common.logging;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}

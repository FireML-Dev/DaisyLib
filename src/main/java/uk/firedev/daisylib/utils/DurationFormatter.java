package uk.firedev.daisylib.utils;

import org.jspecify.annotations.NonNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public record DurationFormatter(@NonNull TimeUnit timeUnit) {

    public String format(long value) {
        long seconds = timeUnit.toSeconds(value);

        // Convert seconds to a Duration
        Duration duration = Duration.ofSeconds(seconds);

        // Calculate hours, minutes, and remaining seconds
        long days = duration.toDaysPart();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long remainingSeconds = duration.toSecondsPart();

        StringBuilder builder = new StringBuilder();

        appendUnit(builder, days, Unit.DAY);
        appendUnit(builder, hours, Unit.HOUR);
        appendUnit(builder, minutes, Unit.MINUTE);
        appendUnit(builder, remainingSeconds, Unit.SECOND);

        return builder.toString();
    }

    private static void appendUnit(@NonNull StringBuilder builder, long value, DurationFormatter.@NonNull Unit timeUnit) {
        if (value <= 0) {
            return;
        }
        builder.append(timeUnit.getFormat(value)).append(" ");
    }

    private enum Unit {
        DAY(
            () -> "{day}d",
            "{day}"
        ),
        HOUR(
            () -> "{hour}h",
            "{hour}"
        ),
        MINUTE(
            () -> "{minute}m",
            "{minute}"
        ),
        SECOND(
            () -> "{second}s",
            "{second}"
        );

        private final Supplier<String> formatSupplier;
        private final String variable;

        Unit(@NonNull Supplier<String> formatSupplier, @NonNull String variable) {
            this.formatSupplier = formatSupplier;
            this.variable = variable;
        }

        public String getFormat(long value) {
            return formatSupplier.get().replace(variable, String.valueOf(value));
        }

    }

}

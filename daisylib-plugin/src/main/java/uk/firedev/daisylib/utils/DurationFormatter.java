package uk.firedev.daisylib.api.utils;

import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.local.config.MessageConfig;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class DurationFormatter {

    public static String formatSeconds(long seconds) {
        // Convert seconds to a Duration
        Duration duration = Duration.ofSeconds(seconds);

        // Calculate years, months, days
        LocalDateTime baseDate = LocalDateTime.now().minusSeconds(seconds);
        LocalDateTime now = LocalDateTime.now();

        Period period = Period.between(baseDate.toLocalDate(), now.toLocalDate());
        long years = period.getYears();
        long months = period.getMonths();
        long days = period.getDays();

        // Calculate hours, minutes, and remaining seconds
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long remainingSeconds = duration.toSecondsPart();

        // Format the result
        StringBuilder readableTime = new StringBuilder();
        appendUnit(readableTime, years, TimeUnit.YEAR);
        appendUnit(readableTime, months, TimeUnit.MONTH);
        appendUnit(readableTime, days, TimeUnit.DAY);
        appendUnit(readableTime, hours, TimeUnit.HOUR);
        appendUnit(readableTime, minutes, TimeUnit.MINUTE);
        appendUnit(readableTime, remainingSeconds, TimeUnit.SECOND);

        // Remove last space if present
        if (readableTime.charAt(readableTime.length() - 1) == ' ') {
            readableTime.setLength(readableTime.length() - 1);
        }

        return readableTime.toString();
    }

    private static void appendUnit(@NotNull StringBuilder sb, long value, @NotNull TimeUnit timeUnit) {
        if (value > 0) {
            boolean multiple = value > 1;
            sb.append(value).append(timeUnit.getFormattedUnit(multiple)).append(" ");
        } else if (timeUnit.equals(TimeUnit.SECOND)) {
            sb.append(0).append(timeUnit.getFormattedUnit(true)).append(" ");
        }
    }

    private enum TimeUnit {
        YEAR,
        MONTH,
        DAY,
        HOUR,
        MINUTE,
        SECOND;

        public String getFormattedUnit(boolean isMultiple) {
            String key = isMultiple ? "multiple" : "single";
            return MessageConfig.getInstance().getConfig().getString("time-units." + this.toString().toLowerCase() + "." + key);
        }

    }

}

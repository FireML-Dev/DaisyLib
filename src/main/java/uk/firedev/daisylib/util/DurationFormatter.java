package uk.firedev.daisylib.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.jspecify.annotations.NonNull;
import uk.firedev.messagelib.message.ComponentMessage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public record DurationFormatter(@NonNull TimeUnit timeUnit) {

    public Component format(long value) {
        long seconds = timeUnit.toSeconds(value);

        // Convert seconds to a Duration
        Duration duration = Duration.ofSeconds(seconds);

        // Calculate days, hours, minutes, and remaining seconds
        long days = duration.toDaysPart();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long remainingSeconds = duration.toSecondsPart();

        // Format the result
        List<Component> list = new ArrayList<>();

        appendUnit(list, days, Unit.DAY);
        appendUnit(list, hours, Unit.HOUR);
        appendUnit(list, minutes, Unit.MINUTE);
        appendUnit(list, remainingSeconds, Unit.SECOND);

        return Component.join(JoinConfiguration.separator(Component.space()), list);
    }

    private static void appendUnit(@NonNull List<Component> list, long value, DurationFormatter.@NonNull Unit timeUnit) {
        if (value <= 0) {
            return;
        }
        list.add(timeUnit.getFormat(value).toSingleMessage().get());
    }

    private enum Unit {
        DAY(
            () -> ComponentMessage.componentMessage("{day}d"),
            "{day}"
        ),
        HOUR(
            () -> ComponentMessage.componentMessage("{hour}h"),
            "{hour}"
        ),
        MINUTE(
            () -> ComponentMessage.componentMessage("{minute}m"),
            "{minute}"
        ),
        SECOND(
            () -> ComponentMessage.componentMessage("{second}s"),
            "{second}"
        );

        private final Supplier<ComponentMessage> formatSupplier;
        private final String variable;

        Unit(@NonNull Supplier<ComponentMessage> formatSupplier, @NonNull String variable) {
            this.formatSupplier = formatSupplier;
            this.variable = variable;
        }

        public ComponentMessage getFormat(long value) {
            return formatSupplier.get().replace(this.variable, value);
        }

    }

}

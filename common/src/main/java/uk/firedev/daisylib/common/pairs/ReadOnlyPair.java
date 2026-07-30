package uk.firedev.daisylib.common.pairs;

import org.jspecify.annotations.Nullable;

/**
 * A read-only version of {@link Pair}.
 */
public record ReadOnlyPair<L, R>(@Nullable L left, @Nullable R right) {

    public static <L, R> ReadOnlyPair<L, R> empty() {
        return new ReadOnlyPair<>(null, null);
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return left == null && right == null;
    }

}
package uk.firedev.daisylib.pairs;

import org.jspecify.annotations.Nullable;

/**
 * A read-only version of {@link Pair}.
 */
public record ReadOnlyPair<L, R>(@Nullable L left, @Nullable R right) {

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return left == null && right == null;
    }

}
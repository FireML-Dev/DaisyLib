package uk.firedev.daisylib.utils;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record ReadOnlyPair<L, R>(L left, R right) {

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

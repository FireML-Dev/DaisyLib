package uk.firedev.daisylib.common.pairs;

import org.jspecify.annotations.Nullable;

/**
 * Contains a pair of elements.
 */
public class Pair<L, R> {

    private L left;
    private R right;

    public Pair(@Nullable L left, @Nullable R right) {
        this.left = left;
        this.right = right;
    }

    public @Nullable L left() {
        return left;
    }

    public void left(@Nullable L left) {
        this.left = left;
    }

    public @Nullable R right() {
        return right;
    }

    public void right(@Nullable R right) {
        this.right = right;
    }

}

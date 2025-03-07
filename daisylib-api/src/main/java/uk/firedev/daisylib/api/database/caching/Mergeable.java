package uk.firedev.daisylib.api.database.caching;

import org.jetbrains.annotations.Nullable;

public interface Mergeable<E> {

    void mergeData(@Nullable E mergeable);

}

package uk.firedev.daisylib.api.database.caching;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Cache<E extends Mergeable<E>> {

    private final E instance;

    private Cache(E instance, Supplier<E> dataSupplier) {
        this.instance = instance;
        loadData(dataSupplier);
    }

    /**
     * Creates a new cache for the provided instance
     * @param instance The instance to use
     * @param dataSupplier A supplier for data to merge with this instance
     * @return A cache for the provided instance
     */
    public static <E extends Mergeable<E>> Cache<E> create(E instance, Supplier<E> dataSupplier) {
        return new Cache<>(instance, dataSupplier);
    }

    /**
     * Creates a new cache for the provided instance
     * @param instance The instance to use
     * @return A cache for the provided instance
     */
    public static <E extends Mergeable<E>> Cache<E> create(E instance) {
        return new Cache<>(instance, () -> null);
    }

    /**
     * Loads the required data and merges it with the data in the cache
     */
    private void loadData(Supplier<E> dataSupplier) {
        CompletableFuture.supplyAsync(dataSupplier).thenAccept(instance::mergeData);
    }

    /**
     * @return This cache's instance
     */
    public E get() {
        return instance;
    }

}

package uk.firedev.daisylib.registry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface Registry<T extends RegistryItem> {

    /**
     * @return An immutable copy of the current registry.
     */
    @NotNull Map<String, T> getRegistry();

    /**
     * Get a value from the registry.
     * @param key The key to look for.
     * @return The value, or null if not found.
     */
    @Nullable T get(@NotNull String key);

    /**
     * Get a value from the registry, or a default value if not found.
     * @param key The key to look for.
     * @param defaultValue The default value to return if not found.
     * @return The value, or the default value if not found.
     */
    @NotNull T getOrDefault(@NotNull String key, @NotNull T defaultValue);

    /**
     * Unregister a key from the registry.
     * @param key The key to unregister.
     * @return True if the key was unregistered, false if not found.
     */
    boolean unregister(@NotNull String key);

    /**
     * Unregister a value from the registry.
     * @param value The value to unregister.
     * @return True if the value was unregistered, false if not found.
     */
    default boolean unregister(@NotNull T value) {
        return unregister(value.getKey());
    }

    /**
     * Register a value in the registry.
     * @param value The value to register.
     * @param force Whether to force the registration, overwriting any existing value.
     * @return True if the value was registered, false if a value with the same key already exists and force is false.
     */
    boolean register(@NotNull T value, boolean force);

    /**
     * Register a value in the registry.
     * @param value The value to register.
     * @return True if the value was registered, false if a value with the same key already exists.
     */
    default boolean register(@NotNull T value) {
        return register(value, false);
    }

}

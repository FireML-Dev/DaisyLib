package uk.firedev.daisylib.common.addons.reward;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.registry.Registry;

import java.util.Map;
import java.util.TreeMap;

public class RewardAddonRegistry implements Registry<RewardAddon> {

    private static final RewardAddonRegistry instance = new RewardAddonRegistry();

    private final Map<String, RewardAddon> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private RewardAddonRegistry() {}

    public static @NonNull RewardAddonRegistry get() {
        return instance;
    }

    @Override
    public boolean isEmpty() {
        return registry.isEmpty();
    }

    @Override
    public void clear() {
        registry.clear();
    }

    /**
     * @return An immutable copy of the current registry.
     */
    @Override
    public @NonNull Map<String, RewardAddon> getRegistry() {
        return Map.copyOf(registry);
    }

    /**
     * Get a value from the registry.
     *
     * @param key The key to look for.
     * @return The value, or null if not found.
     */
    @Override
    public @Nullable RewardAddon get(@NonNull String key) {
        return registry.get(key);
    }

    /**
     * Get a value from the registry, or a default value if not found.
     *
     * @param key          The key to look for.
     * @param defaultValue The default value to return if not found.
     * @return The value, or the default value if not found.
     */
    @Override
    public @NonNull RewardAddon getOrDefault(@NonNull String key, @NonNull RewardAddon defaultValue) {
        return registry.getOrDefault(key, defaultValue);
    }

    /**
     * Unregister a key from the registry.
     *
     * @param key The key to unregister.
     * @return True if the key was unregistered, false if not found.
     */
    @Override
    public boolean unregister(@NonNull String key) {
        return registry.remove(key) != null;
    }

    /**
     * Register a value in the registry.
     *
     * @param value The value to register.
     * @param force Whether to force the registration, overwriting any existing value.
     * @return True if the value was registered, false if a value with the same key already exists and force is false.
     */
    @Override
    public boolean register(@NonNull RewardAddon value, boolean force) {
        if (!force && registry.containsKey(value.getKey())) {
            return false;
        }
        registry.put(value.getKey(), value);
        return true;
    }

}

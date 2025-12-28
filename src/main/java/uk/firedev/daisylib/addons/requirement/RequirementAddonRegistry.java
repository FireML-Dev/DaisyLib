package uk.firedev.daisylib.addons.requirement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.registry.Registry;

import java.util.Map;
import java.util.TreeMap;

public class RequirementAddonRegistry implements Registry<RequirementAddon> {

    private static final RequirementAddonRegistry instance = new RequirementAddonRegistry();

    private final Map<String, RequirementAddon> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private RequirementAddonRegistry() {}

    public static @NotNull RequirementAddonRegistry get() {
        return instance;
    }

    @NotNull
    @Override
    public Map<String, RequirementAddon> getRegistry() {
        return Map.copyOf(registry);
    }

    @Nullable
    @Override
    public RequirementAddon get(@NotNull String key) {
        return registry.get(key);
    }

    @NotNull
    @Override
    public RequirementAddon getOrDefault(@NotNull String key, @NotNull RequirementAddon defaultValue) {
        return registry.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean unregister(@NotNull String key) {
        return registry.remove(key) != null;
    }

    @Override
    public boolean register(@NotNull RequirementAddon value, boolean force) {
        if (!force && registry.containsKey(value.getKey())) {
            return false;
        }
        registry.put(value.getKey(), value);
        return true;
    }

    /**
     * Checks if this registry is empty.
     *
     * @return Whether this registry is empty.
     */
    @Override
    public boolean isEmpty() {
        return registry.isEmpty();
    }

    /**
     * Removes all items from this registry.
     */
    @Override
    public void clear() {
        registry.clear();
    }

}

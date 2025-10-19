package uk.firedev.daisylib.addons.requirement;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.addons.Addon;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class RequirementAddon extends Addon {

    private static final TreeMap<String, RequirementAddon> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * @deprecated Use {@link #getRegistry()} instead.
     */
    @Deprecated(forRemoval = true)
    public static Map<String, RequirementAddon> getLoadedAddons() {
        return getRegistry();
    }

    public static Map<String, RequirementAddon> getRegistry() {
        return Map.copyOf(registry);
    }

    public static @Nullable RequirementAddon get(@NotNull String identifier) {
        return registry.get(identifier);
    }

    public static boolean unregister(@NotNull String identifier) {
        if (!registry.containsKey(identifier)) {
            return false;
        }
        registry.remove(identifier);
        return true;
    }

    public RequirementAddon() {}

    /**
     * Checks if a player meets this requirement.
     * @param data The data to check against
     * @param value The value to check
     */
    public abstract boolean checkRequirement(@NotNull RequirementData data, @NotNull List<String> value);

    /**
     * The identifier for this Requirement
     * @return The identifier for this Requirement
     */
    public abstract @NotNull String getIdentifier();

    public abstract @NotNull String getAuthor();

    public abstract @NotNull Plugin getOwningPlugin();

    public boolean register() {
        return register(false);
    }
    
    public boolean register(boolean force) {
        if (!force && registry.containsKey(getIdentifier())) {
            return false;
        }
        registry.put(getIdentifier(), this);
        return true;
    }

    public boolean unregister() {
        return unregister(getIdentifier());
    }

}

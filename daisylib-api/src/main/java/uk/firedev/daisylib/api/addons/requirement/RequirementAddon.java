package uk.firedev.daisylib.api.addons.requirement;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.addons.Addon;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class RequirementAddon extends Addon {

    private static final TreeMap<String, RequirementAddon> loadedAddons = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public static Map<String, RequirementAddon> getLoadedAddons() {
        return Map.copyOf(loadedAddons);
    }

    public static @Nullable RequirementAddon get(@NotNull String identifier) {
        return loadedAddons.get(identifier);
    }

    public static boolean unregister(@NotNull String identifier) {
        if (!loadedAddons.containsKey(identifier)) {
            return false;
        }
        loadedAddons.remove(identifier);
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
        if (loadedAddons.containsKey(getIdentifier())) {
            return false;
        }
        loadedAddons.put(getIdentifier(), this);
        return true;
    }

    public boolean unregister() {
        return unregister(getIdentifier());
    }

}

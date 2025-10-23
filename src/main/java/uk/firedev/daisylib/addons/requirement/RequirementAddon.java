package uk.firedev.daisylib.addons.requirement;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.addons.Addon;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class RequirementAddon extends Addon {

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
    public abstract @NotNull String getKey();

    public abstract @NotNull String getAuthor();

    public abstract @NotNull Plugin getPlugin();

    public boolean register() {
        return register(false);
    }
    
    public boolean register(boolean force) {
        return RequirementAddonRegistry.get().register(this, force);
    }

    public boolean unregister() {
        return RequirementAddonRegistry.get().unregister(this);
    }

}

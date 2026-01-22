package uk.firedev.daisylib.addons.requirement;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.Addon;

import java.util.List;

public abstract class RequirementAddon extends Addon {

    public RequirementAddon() {}

    /**
     * Checks if a player meets this requirement.
     * @param data The data to check against
     * @param value The value to check
     */
    public abstract boolean checkRequirement(@NonNull RequirementData data, @NonNull List<String> value);

    /**
     * The identifier for this Requirement
     * @return The identifier for this Requirement
     */
    public abstract @NonNull String getKey();

    public abstract @NonNull String getAuthor();

    public abstract @NonNull Plugin getPlugin();

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

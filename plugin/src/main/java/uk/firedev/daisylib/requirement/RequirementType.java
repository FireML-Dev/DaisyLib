package uk.firedev.daisylib.requirement;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.registry.Registry;
import uk.firedev.daisylib.registry.RegistryItem;

import java.util.List;

 /// A way to check if a player meets a certain requirement.
public abstract class RequirementType implements RegistryItem {

    public RequirementType() {}

    /// Checks if a player meets this requirement.
    /// @param context The context to check
    /// @param values The values to check this context against
    public abstract boolean check(@NonNull RequirementContext context, @NonNull List<String> values);

     /// The identifier for this Requirement
     /// @return The identifier for this Requirement
    public abstract @NonNull String getIdentifier();

    @Override
    public @NonNull String getKey() {
        return getIdentifier();
    }

    public abstract @NonNull String getAuthor();

    public abstract @NonNull Plugin getPlugin();

    protected void debugLogStatus(boolean passed) {
        String status = passed ? "passed" : "failed";
        DaisyLib.get().getLogging().debug(getIdentifier() + " Requirement " + status + ".");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RequirementType type)) {
            return false;
        }
        return getIdentifier().equals(type.getIdentifier());
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

}

package uk.firedev.daisylib.requirement;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.registry.RegistryItem;

import java.util.List;

 /// A way to check if a player meets a certain requirement.
public abstract class RequirementType extends RegistryItem {

    public RequirementType() {}

    /// Checks if a player meets this requirement.
    /// @param context The context to check
    /// @param values The values to check this context against
    public abstract boolean check(@NonNull RequirementContext context, @NonNull List<String> values);

    public abstract @NonNull String getKey();

    public abstract @NonNull String getAuthor();

    public abstract @NonNull Plugin getPlugin();

    protected void debugLogStatus(boolean passed) {
        String status = passed ? "passed" : "failed";
        DaisyLib.get().getLogging().debug(getKey() + " Requirement " + status + ".");
    }

}

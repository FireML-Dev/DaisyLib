package uk.firedev.daisylib.requirement.types;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.registry.Registry;
import uk.firedev.daisylib.requirement.RequirementContext;
import uk.firedev.daisylib.requirement.RequirementType;

import java.util.List;
import java.util.Objects;

/**
 * A RequirementType to check that specific RewardTypes are loaded.
 */
public class RewardTypeRequirementType extends RequirementType {

    /**
     * Checks if a player meets this requirement.
     *
     * @param context The context to check
     * @param values  The values to check this context against
     */
    @Override
    public boolean check(@NonNull RequirementContext context, @NonNull List<String> values) {
        boolean pass = values.stream()
            .filter(Objects::nonNull)
            .allMatch(value -> Registry.REWARD_TYPE.get(value) != null);
        debugLogStatus(pass);
        return pass;
    }

    /**
     * The identifier for this Requirement
     *
     * @return The identifier for this Requirement
     */
    @Override
    public @NonNull String getIdentifier() {
        return "RewardType";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull Plugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return true;
    }

}

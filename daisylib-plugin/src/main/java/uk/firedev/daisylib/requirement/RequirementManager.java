package uk.firedev.daisylib.requirement;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.requirement.requirements.*;

import java.util.*;
import java.util.function.BiFunction;

public class RequirementManager {

    private static RequirementManager instance;

    private final TreeMap<String, RequirementType> requirements = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private boolean loaded = false;

    private RequirementManager() {}

    public static RequirementManager getInstance() {
        if (instance == null) {
            instance = new RequirementManager();
        }
        return instance;
    }

    public void load() {
        if (!isLoaded()) {
            Loggers.info(DaisyLib.getInstance().getComponentLogger(), "Loading RequirementManager!");
            new EXPRequirement().register();
            new HealthRequirement().register();
            new HoldingRequirement().register();
            new MoneyRequirement().register();
            new PermissionRequirement().register();
            loaded = true;
        }
    }

    public boolean isLoaded() { return loaded; }

    /**
     * Register a custom requirement.
     * @param requirementType The requirement type instance you wish to register
     * @return Whether the requirement type was added or not
     */
    public boolean registerRequirement(@NotNull RequirementType requirementType) {
        String identifier = requirementType.getIdentifier();
        if (requirements.containsKey(identifier)) {
            return false;
        }
        Loggers.info(DaisyLib.getInstance().getComponentLogger(),
                "<green>Registered <gold>" + identifier + "</gold> Requirement by <gold>" + requirementType.getAuthor() + "</gold> from the plugin <aqua>" + requirementType.getPlugin().getName()
        );
        requirements.put(identifier, requirementType);
        return true;
    }

    /**
     * Unregister a custom requirement.
     * @param requirementName The requirement you wish to unregister
     * @return Whether the requirement type was removed or not
     */
    public boolean unregisterRequirement(@NotNull String requirementName) {
        if (!requirements.containsKey(requirementName)) {
            return false;
        }
        requirements.remove(requirementName);
        return true;
    }

    public @Nullable RequirementType getRequirementType(@NotNull String identifier) {
        return requirements.get(identifier);
    }

    /**
     * @return A read-only copy of the requirement map
     */
    public Map<String, RequirementType> getRegisteredRequirements() { return Map.copyOf(requirements); }

    public List<RequirementType> getRegisteredRequirementTypes() {
        return new ArrayList<>(requirements.values());
    }

    /**
     * Alternative method of registering requirement types.
     * @param identifier The type's identifier
     * @param author The author of this requirement type
     * @param plugin The plugin responsible for this requirement type
     * @param checkLogic The code to run when checking the requirements. The List<String> will be the provided values to check against.
     * @return Whether this type was registered or not
     */
    public boolean registerRequirement(@NotNull String identifier, @NotNull String author, @NotNull Plugin plugin, @NotNull BiFunction<RequirementData, List<String>, Boolean> checkLogic) {

        return registerRequirement(new RequirementType() {

            @Override
            public boolean checkRequirement(@NotNull RequirementData data, @NotNull List<String> value) {
                return checkLogic.apply(data, value);
            }

            @Override
            public @NotNull String getIdentifier() {
                return identifier;
            }

            @Override
            public @NotNull String getAuthor() {
                return author;
            }

            @Override
            public @NotNull Plugin getPlugin() {
                return plugin;
            }
        });

    }

}

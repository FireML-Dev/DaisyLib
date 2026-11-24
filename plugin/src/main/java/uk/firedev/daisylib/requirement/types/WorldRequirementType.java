package uk.firedev.daisylib.requirement.types;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.requirement.RequirementContext;
import uk.firedev.daisylib.requirement.RequirementType;

import java.util.List;

public class WorldRequirementType extends RequirementType {

    @Override
    public boolean check(@NonNull RequirementContext context, @NonNull List<String> values) {
        World world = context.world();
        if (world == null) {
            return false;
        }
        String worldName = world.getName();
        String worldKey = world.getKey().asString();
        for (String value : values) {
            if (worldName.equalsIgnoreCase(value) || worldKey.equals(value)) {
                debugLogStatus(true);
                return true;
            }
        }
        debugLogStatus(false);
        return false;
    }

    @Override
    public @NonNull String getIdentifier() {
        return "World";
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

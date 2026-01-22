package uk.firedev.daisylib.addons.requirement.types;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementData;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

import java.util.List;

public class WorldRequirementAddon extends RequirementAddon {

    @Override
    public boolean checkRequirement(@NonNull RequirementData data, @NonNull List<String> values) {
        World world = data.getWorld();
        if (world == null) {
            return false;
        }
        String worldName = world.getName();
        for (String value : values) {
            if (worldName.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NonNull String getKey() {
        return "World";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull Plugin getPlugin() {
        return DaisyLibPlugin.getInstance();
    }

}

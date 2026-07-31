package uk.firedev.daisylib.common.addons.requirement.defaults;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.common.DaisyLib;
import uk.firedev.daisylib.common.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.common.addons.requirement.RequirementData;

import java.util.List;

public class WorldRequirementAddon extends RequirementAddon {

    @Override
    public boolean check(@NonNull RequirementData data, @NonNull List<String> values) {
        World world = data.world();
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
        return DaisyLib.get().getPlugin();
    }

}

package uk.firedev.daisylib.addons.requirement.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementData;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;
import uk.firedev.daisylib.util.Utils;

import java.util.List;

public class ExpRequirementAddon extends RequirementAddon {

    @Override
    public boolean checkRequirement(@NotNull RequirementData data, @NotNull List<String> values) {
        Player player = data.getPlayer();
        if (player == null) {
            return false;
        }
        int experiencePoints = player.calculateTotalExperiencePoints();
        for (String value : values) {
            Integer amount = Utils.getInt(value);
            if (amount == null) {
                Loggers.warn(getClass(), value + " is not a valid integer");
                continue;
            }
            if (experiencePoints >= amount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getKey() {
        return "Exp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return DaisyLibPlugin.getInstance();
    }

}

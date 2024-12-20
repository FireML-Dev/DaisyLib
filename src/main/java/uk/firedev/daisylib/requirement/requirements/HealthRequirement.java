package uk.firedev.daisylib.requirement.requirements;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.requirement.RequirementData;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.utils.ObjectUtils;

import java.util.List;

public class HealthRequirement implements RequirementType {

    @Override
    public boolean checkRequirement(@NotNull RequirementData data, @NotNull List<String> values) {
        Player player = data.getPlayer();
        if (player == null) {
            return false;
        }
        double currentHealth = player.getHealth();
        for (String value : values) {
            if (!ObjectUtils.isDouble(value)) {
                Loggers.warn(getComponentLogger(), value + " is not a valid double");
                continue;
            }
            double healthNeeded = Double.parseDouble(value);
            if (currentHealth >= healthNeeded) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "HEALTH";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return DaisyLib.getInstance();
    }

}

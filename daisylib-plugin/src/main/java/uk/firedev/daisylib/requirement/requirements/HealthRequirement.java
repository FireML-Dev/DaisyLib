package uk.firedev.daisylib.requirement.requirements;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.utils.ObjectUtils;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.requirement.RequirementData;
import uk.firedev.daisylib.requirement.RequirementType;

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
            Double amount = ObjectUtils.getDouble(value);
            if (amount == null) {
                Loggers.warn(getComponentLogger(), value + " is not a valid double");
                continue;
            }
            if (currentHealth >= amount) {
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

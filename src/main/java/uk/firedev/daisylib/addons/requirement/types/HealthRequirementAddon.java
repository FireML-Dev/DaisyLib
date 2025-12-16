package uk.firedev.daisylib.addons.requirement.types;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementData;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.utils.ObjectUtils;

import java.util.List;

public class HealthRequirementAddon extends RequirementAddon {

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
                Loggers.warn(getClass(), value + " is not a valid double");
                continue;
            }
            if (currentHealth >= amount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getKey() {
        return "Health";
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

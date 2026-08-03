package uk.firedev.daisylib.addons.requirement.defaults;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementData;
import uk.firedev.daisylib.utils.CommonUtils;

import java.util.List;

public class HealthRequirementAddon extends RequirementAddon {

    @Override
    public boolean check(@NonNull RequirementData data, @NonNull List<String> values) {
        Player player = data.player();
        if (player == null) {
            return false;
        }
        double currentHealth = player.getHealth();
        for (String value : values) {
            Double amount = CommonUtils.getDouble(value);
            if (amount == null) {
                getLogging().warn(value + " is not a valid double");
                continue;
            }
            if (currentHealth >= amount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NonNull String getKey() {
        return "Health";
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

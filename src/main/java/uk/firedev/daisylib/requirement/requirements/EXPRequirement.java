package uk.firedev.daisylib.requirement.requirements;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.utils.ObjectUtils;

public class EXPRequirement implements RequirementType {

    @Override
    public boolean checkRequirement(@NotNull Player player, @NotNull String value) {
        if (!ObjectUtils.isInt(value)) {
            return false;
        }
        int expNeeded = Integer.parseInt(value);
        return player.calculateTotalExperiencePoints() >= expNeeded;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "EXP";
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

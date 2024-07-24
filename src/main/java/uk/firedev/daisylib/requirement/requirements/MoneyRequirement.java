package uk.firedev.daisylib.requirement.requirements;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.utils.ObjectUtils;

public class MoneyRequirement implements RequirementType {

    @Override
    public boolean checkRequirement(@NotNull Player player, @NotNull String value) {
         if (!ObjectUtils.isDouble(value)) {
             return false;
         }
         double amount = Double.parseDouble(value);
         if (VaultManager.getEconomy() == null) {
             Loggers.warn(getComponentLogger(), "Vault economy not found! Please enable one to use the " + getIdentifier() + " requirement.");
             return false;
         } else {
             return VaultManager.getEconomy().has(player, amount);
         }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "MONEY";
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

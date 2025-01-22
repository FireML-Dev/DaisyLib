package uk.firedev.daisylib.requirement.requirements;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.requirement.RequirementData;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.api.utils.ObjectUtils;

import java.util.List;

public class MoneyRequirement implements RequirementType {

    @Override
    public boolean checkRequirement(@NotNull RequirementData data, @NotNull List<String> values) {
         if (data.getPlayer() == null) {
             return false;
         }
        if (VaultManager.getInstance().getEconomy() == null) {
            Loggers.warn(getComponentLogger(), "Vault economy not found! Please enable one to use the " + getIdentifier() + " requirement.");
            return false;
        }
        for (String value : values) {
            double amount;
            if (!ObjectUtils.isDouble(value)) {
                Loggers.warn(getComponentLogger(), value + " is not a valid double");
                continue;
            }
            amount = Double.parseDouble(value);
            if (VaultManager.getInstance().getEconomy().has(data.getPlayer(), amount)) {
                return true;
            }
        }
        return false;
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

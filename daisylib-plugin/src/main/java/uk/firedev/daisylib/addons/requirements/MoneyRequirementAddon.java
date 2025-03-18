package uk.firedev.daisylib.addons.requirements;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.api.utils.ObjectUtils;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.api.addons.requirement.RequirementData;

import java.util.List;

public class MoneyRequirementAddon extends RequirementAddon {

    @Override
    public boolean checkRequirement(@NotNull RequirementData data, @NotNull List<String> values) {
         if (data.getPlayer() == null) {
             return false;
         }
        Economy economy = VaultManager.getInstance().getEconomy();
        if (economy == null) {
            Loggers.warn(getClass(), "Vault Economy not found! Please enable one to use this requirement.");
            return false;
        }
        for (String value : values) {
            Double amount = ObjectUtils.getDouble(value);
            if (amount == null) {
                Loggers.warn(getClass(), value + " is not a valid double");
                continue;
            }
            if (economy.has(data.getPlayer(), amount)) {
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
    public @NotNull Plugin getOwningPlugin() {
        return DaisyLib.getInstance();
    }

}

package uk.firedev.daisylib.addons.requirement.types;

import net.milkbowl.vault2.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.utils.ObjectUtils;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.addons.requirement.RequirementData;

import java.math.BigDecimal;
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
            if (economy.has(getPlugin().getName(), data.getPlayer().getUniqueId(), BigDecimal.valueOf(amount))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getKey() {
        return "Money";
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

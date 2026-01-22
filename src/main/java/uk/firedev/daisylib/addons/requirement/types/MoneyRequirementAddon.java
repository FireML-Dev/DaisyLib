package uk.firedev.daisylib.addons.requirement.types;

import net.milkbowl.vault2.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementData;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;
import uk.firedev.daisylib.util.Utils;
import uk.firedev.daisylib.util.VaultManager;

import java.math.BigDecimal;
import java.util.List;

public class MoneyRequirementAddon extends RequirementAddon {

    @Override
    public boolean checkRequirement(@NonNull RequirementData data, @NonNull List<String> values) {
        if (data.getPlayer() == null) {
            return false;
        }
        Economy economy = VaultManager.getInstance().getEconomy();
        if (economy == null) {
            Loggers.warn(getClass(), "Vault Economy not found! Please enable one to use this requirement.");
            return false;
        }
        for (String value : values) {
            Double amount = Utils.getDouble(value);
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
    public @NonNull String getKey() {
        return "Money";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull Plugin getPlugin() {
        return DaisyLibPlugin.getInstance();
    }

}

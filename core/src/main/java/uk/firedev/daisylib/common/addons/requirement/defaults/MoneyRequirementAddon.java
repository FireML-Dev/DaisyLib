package uk.firedev.daisylib.common.addons.requirement.defaults;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.common.DaisyLib;
import uk.firedev.daisylib.common.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.common.addons.requirement.RequirementData;
import uk.firedev.daisylib.common.external.vault.VaultWrapper;
import uk.firedev.daisylib.common.utils.CommonUtils;

import java.util.List;

public class MoneyRequirementAddon extends RequirementAddon {

    @Override
    public boolean check(@NonNull RequirementData data, @NonNull List<String> values) {
        if (data.player() == null) {
            return false;
        }
        Economy economy = VaultWrapper.get().getEconomyOrNull();
        if (economy == null) {
            getLogging().warn("Vault Economy not found. Failing " + getKey() + " requirement.");
            return false;
        }
        for (String value : values) {
            Double amount = CommonUtils.getDouble(value);
            if (amount == null) {
                getLogging().warn(value + " is not a valid double");
                continue;
            }
            if (economy.has(data.player(), amount)) {
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
        return DaisyLib.get().getPlugin();
    }

}

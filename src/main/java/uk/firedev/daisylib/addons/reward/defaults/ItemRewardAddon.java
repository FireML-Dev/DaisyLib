package uk.firedev.daisylib.addons.reward.defaults;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.utils.CommonUtils;

public class ItemRewardAddon extends RewardAddon {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        String[] splitValue = value.split(",");
        ItemStack item = CommonUtils.getItem(splitValue[0]);
        if (item == null) {
            getLogging().info("Invalid item specified: " + splitValue[0]);
            return;
        }
        int quantity = 1;
        if (splitValue.length >= 2) {
            Integer amount = CommonUtils.getInt(splitValue[1]);
            if (amount == null) {
                getLogging().info("Invalid number specified: " + splitValue[1]);
                return;
            }
            quantity = Math.max(amount, 1);
        }
        for (int i = 0; i < quantity; ++i) {
            CommonUtils.giveItem(item, player);
        }
    }

    @Override
    public @NonNull String getKey() {
        return "Item";
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

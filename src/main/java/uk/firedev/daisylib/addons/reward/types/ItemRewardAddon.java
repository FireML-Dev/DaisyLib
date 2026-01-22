package uk.firedev.daisylib.addons.reward.types;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;
import uk.firedev.daisylib.util.Utils;


public class ItemRewardAddon extends RewardAddon {

    @Override
    public void doReward(@NonNull Player player, @NonNull String value) {
        String[] splitValue = value.split(",");
        ItemStack item = Utils.getItem(splitValue[0]);
        if (item == null) {
            Loggers.info(getClass(), "Invalid item specified: " + splitValue[0]);
            return;
        }
        int quantity = 1;
        if (splitValue.length >= 2) {
            Integer amount = Utils.getInt(splitValue[1]);
            if (amount == null) {
                Loggers.info(getClass(), "Invalid number specified: " + splitValue[1]);
                return;
            }
            quantity = Math.max(amount, 1);
        }
        for (int i = 0; i < quantity; ++i) {
            player.give(item);
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
        return DaisyLibPlugin.getInstance();
    }

}

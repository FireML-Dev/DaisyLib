package uk.firedev.daisylib.addons.reward.types;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.utils.ItemUtils;
import uk.firedev.daisylib.utils.ObjectUtils;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.addons.reward.RewardAddon;

public class ItemRewardAddon extends RewardAddon {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        String[] splitValue = value.split(",");
        ItemStack item = ItemUtils.getItem(splitValue[0]);
        if (item == null) {
            Loggers.info(getClass(), "Invalid item specified: " + splitValue[0]);
            return;
        }
        int quantity = 1;
        if (splitValue.length >= 2) {
            Integer amount = ObjectUtils.getInt(splitValue[1]);
            if (amount == null) {
                Loggers.info(getClass(), "Invalid number specified: " + splitValue[1]);
                return;
            }
            quantity = Math.max(amount, 1);
        }
        item.setAmount(quantity);
        for (int i = 0; i < quantity; ++i) {
            ItemUtils.giveItem(item, player);
        }
    }

    @Override
    public @NotNull String getKey() {
        return "Item";
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

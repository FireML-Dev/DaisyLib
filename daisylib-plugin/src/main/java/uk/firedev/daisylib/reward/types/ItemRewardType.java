package uk.firedev.daisylib.reward.types;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.utils.ItemUtils;
import uk.firedev.daisylib.api.utils.ObjectUtils;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardType;

public class ItemRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        
        String[] splitValue = value.split(",");
        Material material = ItemUtils.getMaterial(splitValue[0]);
        if (material == null) {
            Loggers.info(getComponentLogger(), "Invalid material specified for RewardType " + getIdentifier() + ": " + splitValue[0]);
            return;
        }
        int quantity = 1;
        if (splitValue.length >= 2) {
            Integer amount = ObjectUtils.getInt(splitValue[1]);
            if (amount == null) {
                Loggers.info(getComponentLogger(), "Invalid number specified for RewardType " + getIdentifier() + ": " + splitValue[1]);
                return;
            }
            quantity = Math.max(amount, 1);
        }
        ItemStack item = ItemStack.of(material);
        for (int i = 0; i < quantity; ++i) {
            ItemUtils.giveItem(item, player);
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "ITEM";
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

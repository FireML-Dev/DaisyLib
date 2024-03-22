package uk.firedev.daisylib.reward.types;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.ItemUtils;

public class ItemRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value, int quantity) {
        ItemStack item = null;
        Material material = ItemUtils.getMaterial(value, null);
        if (material != null) {
            item = new ItemStack(material);
        }
        if (item != null) {
            item.setAmount(Math.max(quantity, 1));
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
    public @NotNull JavaPlugin getPlugin() {
        return DaisyLib.getInstance();
    }

}

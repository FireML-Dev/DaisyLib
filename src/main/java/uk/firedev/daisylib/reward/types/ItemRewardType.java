package uk.firedev.daisylib.reward.types;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.ItemUtils;
import uk.firedev.daisylib.utils.ObjectUtils;

import java.util.logging.Level;

public class ItemRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value) {
        String[] splitValue = value.split(",");
        Material material = ItemUtils.getMaterial(splitValue[0], null);
        if (material == null) {
            Loggers.log(Level.INFO, getLogger(), "Invalid material specified for RewardType " + getIdentifier() + ": " + splitValue[0]);
            return;
        }
        int quantity = 1;
        if (splitValue.length >= 2) {
            if (!ObjectUtils.isInt(splitValue[1])) {
                Loggers.log(Level.INFO, getLogger(), "Invalid number specified for RewardType " + getIdentifier() + ": " + splitValue[1]);
                return;
            }
            quantity = Math.max(Integer.parseInt(splitValue[1]), 1);
        }
        ItemStack item = new ItemStack(material);
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
    public @NotNull JavaPlugin getPlugin() {
        return DaisyLib.getInstance();
    }

}

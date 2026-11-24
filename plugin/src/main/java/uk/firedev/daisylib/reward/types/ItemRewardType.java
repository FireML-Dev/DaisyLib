package uk.firedev.daisylib.reward.types;

import net.md_5.bungee.api.chat.hover.content.ItemSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.CommonUtils;

public class ItemRewardType extends RewardType {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        String[] parsedItem = value.split(",");
        ItemType itemType = CommonUtils.getItemType(parsedItem[0]);
        if (itemType == null) {
            DaisyLib.get().getLogging().warn("Invalid item specified for RewardType " + getIdentifier() + ": " + parsedItem[0]);
            return;
        }
        int quantity = 1;
        if (parsedItem.length > 1) {
            try {
                quantity = Integer.parseInt(parsedItem[1]);
            } catch (NumberFormatException ex) {
                DaisyLib.get().getLogging().warn("Invalid quantity specified for RewardType " + getIdentifier() + ": " + parsedItem[1]);
                return;
            }
        }
        ItemStack item = itemType.createItemStack();
        for (int i = 0; i < quantity; ++i) {
            player.give(item);
        }
    }

    @Override
    public @NonNull String getIdentifier() {
        return "Item";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull JavaPlugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return true;
    }
}

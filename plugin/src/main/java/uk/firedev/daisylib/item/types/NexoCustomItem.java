package uk.firedev.daisylib.item.types;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.item.CustomItem;

@NullMarked
public class NexoCustomItem extends CustomItem {

    @Override
    public String getKey() {
        return "Nexo";
    }

    @Override
    public Plugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return Bukkit.getPluginManager().isPluginEnabled("Nexo");
    }

    @Override
    public String getAuthor() {
        return "FireML";
    }

    @Override
    public @Nullable ItemStack getItemStack(String id) {
        if (!NexoItems.exists(id)) {
            DaisyLib.get().getLogging().debug("Nexo item with id " + id + " doesn't exist.");
            return null;
        }
        final ItemBuilder item = NexoItems.itemFromId(id);
        if (item == null) {
            DaisyLib.get().getLogging().warn("Could not obtain Nexo item " + id);
            return null;
        }
        return item.build();
    }

    @Override
    public @Nullable String convertToString(ItemStack item) {
        String id = NexoItems.idFromItem(item);
        if (id == null) {
            return null;
        }
        return "nexo:" + id;
    }

    @Override
    public boolean isCustomItem(ItemStack item) {
        return NexoItems.idFromItem(item) != null;
    }

    @Override
    public boolean isMatching(ItemStack item, String id) {
        return id.equalsIgnoreCase(NexoItems.idFromItem(item));
    }

}

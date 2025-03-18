package uk.firedev.daisylib.addons.items;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.addons.item.ItemAddon;
import uk.firedev.daisylib.local.DaisyLib;

public class NexoItemAddon extends ItemAddon {

    @Override
    public ItemStack getItem(@NotNull String id) {
        ItemBuilder item = NexoItems.itemFromId(id);
        return item == null ? null : item.build();
    }

    @NotNull
    @Override
    public String getIdentifier() {
        return "Nexo";
    }

    @Override
    public @NotNull Plugin getOwningPlugin() {
        return DaisyLib.getInstance();
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "FireML";
    }

}

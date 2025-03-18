package uk.firedev.daisylib.addons.items;

import com.denizenscript.denizen.objects.ItemTag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.addons.item.ItemAddon;
import uk.firedev.daisylib.local.DaisyLib;

public class DenizenItemAddon extends ItemAddon {

    @Override
    public ItemStack getItem(@NotNull String id) {
        ItemTag item = ItemTag.valueOf(id, false);
        return item == null ? null : item.getItemStack();
    }

    @NotNull
    @Override
    public String getIdentifier() {
        return "Denizen";
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

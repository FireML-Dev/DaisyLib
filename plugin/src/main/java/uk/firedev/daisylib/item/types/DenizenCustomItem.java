package uk.firedev.daisylib.item.types;

import com.denizenscript.denizen.objects.ItemTag;
import com.denizenscript.denizen.scripts.containers.core.ItemScriptContainer;
import com.denizenscript.denizen.scripts.containers.core.ItemScriptHelper;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.item.CustomItem;

@NullMarked
public class DenizenCustomItem extends CustomItem {

    @Override
    public @Nullable ItemStack getItemStack(String id) {
        ItemTag itemTag = ItemTag.valueOf(id, false);
        if (itemTag == null) {
            DaisyLib.get().getLogging().debug("Could not obtain Denizen item " + id);
            return null;
        }
        return itemTag.getItemStack();
    }

    @Override
    public @Nullable String convertToString(ItemStack item) {
        ItemScriptContainer container = ItemScriptHelper.getItemScriptContainer(item);
        if (container == null) {
            return null;
        }
        return "denizen:" + container.getName();
    }

    @Override
    public boolean isCustomItem(ItemStack item) {
        return ItemScriptHelper.getItemScriptContainer(item) != null;
    }

    @Override
    public boolean isMatching(ItemStack item, String id) {
        ItemScriptContainer container = ItemScriptHelper.getItemScriptContainer(item);
        if (container == null) {
            return false;
        }
        return id.equalsIgnoreCase(container.getName());
    }

    @Override
    public String getAuthor() {
        return "FireML";
    }

    @Override
    public String getKey() {
        return "Denizen";
    }

    @Override
    public Plugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return Bukkit.getPluginManager().isPluginEnabled("Denizen");
    }

}

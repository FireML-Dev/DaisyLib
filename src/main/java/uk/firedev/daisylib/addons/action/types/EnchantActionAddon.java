package uk.firedev.daisylib.addons.action.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.local.DaisyLib;

public class EnchantActionAddon extends ActionAddon<EnchantItemEvent> implements Listener {

    @NotNull
    @Override
    public Class<EnchantItemEvent> getEventType() {
        return EnchantItemEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "enchant";
    }

    @EventHandler(ignoreCancelled = true)
    public void onEnchant(EnchantItemEvent event) {
        fireEvent(event, null);
    }

    @NotNull
    @Override
    public Plugin getPlugin() {
        return DaisyLib.getInstance();
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "FireML";
    }

}

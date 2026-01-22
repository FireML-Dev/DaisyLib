package uk.firedev.daisylib.addons.action.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

public class EnchantActionAddon extends ActionAddon<EnchantItemEvent> implements Listener {

    @NonNull
    @Override
    public Class<EnchantItemEvent> getEventType() {
        return EnchantItemEvent.class;
    }

    @NonNull
    @Override
    public String getKey() {
        return "enchant";
    }

    @EventHandler(ignoreCancelled = true)
    public void onEnchant(EnchantItemEvent event) {
        fireEvent(event, null);
    }

    @NonNull
    @Override
    public Plugin getPlugin() {
        return DaisyLibPlugin.getInstance();
    }

    @NonNull
    @Override
    public String getAuthor() {
        return "FireML";
    }

}

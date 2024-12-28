package uk.firedev.daisylib.actions.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.ActionContext;
import uk.firedev.daisylib.actions.ActionType;

public class EnchantActionType extends ActionType {

    @NotNull
    @Override
    public String getActionIdentifier() {
        return "enchant";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEnchant(EnchantItemEvent event) {
        if (event.isCancelled()) {
            return;
        }
        fire(ActionContext.create()
                .withPlayer(event.getEnchanter())
                .withEnchantments(event.getEnchantsToAdd())
                .withItem(event.getItem())
        );
    }
}

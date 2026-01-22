package uk.firedev.daisylib.addons.item;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.addons.Addon;

public abstract class ItemAddon extends Addon {

    public ItemAddon() {}

    public abstract @Nullable ItemStack getItem(@NonNull String id);

    public boolean register(boolean force) {
        return ItemAddonRegistry.get().register(this, force);
    }

    public boolean register() {
        return register(false);
    }

    public boolean unregister() {
        return ItemAddonRegistry.get().unregister(this);
    }

}

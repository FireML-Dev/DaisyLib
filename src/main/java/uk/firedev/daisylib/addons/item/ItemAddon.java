package uk.firedev.daisylib.addons.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.addons.Addon;
import uk.firedev.daisylib.addons.InvalidAddonException;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public abstract class ItemAddon extends Addon {

    public ItemAddon() {}

    public abstract @Nullable ItemStack getItem(@NotNull String id);

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

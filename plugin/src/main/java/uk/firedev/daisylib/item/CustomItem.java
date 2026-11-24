package uk.firedev.daisylib.item;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.registry.Registry;
import uk.firedev.daisylib.registry.RegistryItem;

@NullMarked
public abstract class CustomItem extends RegistryItem {

    /// @return An ItemStack resolved from the given id.
    public abstract @Nullable ItemStack getItemStack(final String id);

    /// @return A string representation of the given item. Example: (denizen:repair_core)
    public abstract @Nullable String convertToString(ItemStack item);

    /// @return Whether the given item is a valid custom item.
    public abstract boolean isCustomItem(ItemStack item);

    /// @return Whether the given item matches the given id.
    public abstract boolean isMatching(ItemStack item, String id);

    /// @return The author of this CustomItem.
    public abstract String getAuthor();

}

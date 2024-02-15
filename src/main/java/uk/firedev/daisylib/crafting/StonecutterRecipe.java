package uk.firedev.daisylib.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.StonecuttingRecipe;
import org.jetbrains.annotations.NotNull;

public class StonecutterRecipe extends StonecuttingRecipe {

    private boolean registered = false;
    private final NamespacedKey key;

    public StonecutterRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull Material source) {
        super(key, result, source);
        this.key = key;
    }

    public StonecutterRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull ItemStack source) {
        super(key, result, new RecipeChoice.ExactChoice(source));
        this.key = key;
    }

    public boolean register() {
        if (registered) {
            return false;
        }
        if (Bukkit.addRecipe(this)) {
            registered = true;
            return true;
        }
        return false;
    }

    public boolean unregister() {
        if (!registered) {
            return false;
        }
        if (Bukkit.removeRecipe(this.key, true)) {
            registered = false;
            return true;
        }
        return false;
    }

}

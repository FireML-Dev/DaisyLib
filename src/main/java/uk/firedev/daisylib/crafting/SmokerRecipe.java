package uk.firedev.daisylib.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

public class SmokerRecipe extends org.bukkit.inventory.SmokingRecipe {

    private boolean registered = false;
    private final NamespacedKey key;

    public SmokerRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull Material source, float experience, int cookingTime) {
        super(key, result, source, experience, cookingTime);
        this.key = key;
    }

    public SmokerRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull ItemStack source, float experience, int cookingTime) {
        super(key, result, new RecipeChoice.ExactChoice(source), experience, cookingTime);
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

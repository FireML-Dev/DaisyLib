package uk.firedev.daisylib.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BlastFurnaceRecipe extends org.bukkit.inventory.BlastingRecipe {

    private boolean registered = false;
    private final NamespacedKey key;

    public BlastFurnaceRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull Material source, float experience, int cookingTime) {
        super(key, result, source, experience, cookingTime);
        this.key = key;
    }

    public BlastFurnaceRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull ItemStack source, float experience, int cookingTime) {
        super(key, result, RecipeUtil.getRecipeChoiceFromItem(source), experience, cookingTime);
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

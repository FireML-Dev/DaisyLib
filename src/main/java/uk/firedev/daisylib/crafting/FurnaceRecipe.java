package uk.firedev.daisylib.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FurnaceRecipe extends org.bukkit.inventory.FurnaceRecipe implements IRecipe {

    private boolean registered = false;
    private final NamespacedKey key;

    public FurnaceRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull Material source, float experience, int cookingTime) {
        super(key, result, source, experience, cookingTime);
        this.key = key;
    }

    public FurnaceRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull ItemStack source, float experience, int cookingTime) {
        super(key, result, RecipeUtil.getRecipeChoiceFromItem(source), experience, cookingTime);
        this.key = key;
    }

    @Override
    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Override
    public NamespacedKey getNamespacedKey() {
        return key;
    }
}

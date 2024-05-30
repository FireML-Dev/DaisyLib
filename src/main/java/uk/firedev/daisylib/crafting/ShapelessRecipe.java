package uk.firedev.daisylib.crafting;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShapelessRecipe extends org.bukkit.inventory.ShapelessRecipe {

    private final NamespacedKey key;
    private final List<ItemStack> recipeItems;
    private boolean registered = false;

    public ShapelessRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull List<ItemStack> recipeItems) {
        super(key, result);
        this.key = key;
        if (recipeItems.size() > 9) {
            this.recipeItems = recipeItems.subList(0, 8);
        } else {
            this.recipeItems = recipeItems;
        }
        buildRecipe();
    }

    private void buildRecipe() {
        recipeItems.forEach(item -> {
            RecipeChoice choice = RecipeUtil.getRecipeChoiceFromItemNullable(item);
            if (choice != null) {
                addIngredient(choice);
            }
        });
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

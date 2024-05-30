package uk.firedev.daisylib.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShapedRecipe extends org.bukkit.inventory.ShapedRecipe {

    private final NamespacedKey key;
    private final List<ItemStack> recipeItems;
    private boolean registered = false;

    public ShapedRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull List<ItemStack> recipeItems) {
        super(key, result);
        this.key = key;
        if (recipeItems.size() > 9) {
            this.recipeItems = recipeItems.subList(0, 8);
        } else {
            this.recipeItems = recipeItems;
        }
        buildRecipe();
    }

    public boolean isRegistered() { return registered; }

    private void buildRecipe() {
        shape("ABC", "DEF", "GHI");
        for (int i = 0; i < 9; ++i) {
            ItemStack item;
            try {
                item = recipeItems.get(i);
            } catch (IndexOutOfBoundsException ex) {
                item = new ItemStack(Material.AIR);
            }
            char loopChar = charFromInt(i);
            setIngredient(loopChar, RecipeUtil.getRecipeChoiceFromItem(item));
        }
    }

    private char charFromInt(int i) {
        if (i > 8 || i < 0) {
            return 'X';
        }
        char[] charMap = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        return charMap[i];
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

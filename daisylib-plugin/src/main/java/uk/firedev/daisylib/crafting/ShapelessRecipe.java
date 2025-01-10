package uk.firedev.daisylib.crafting;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShapelessRecipe extends org.bukkit.inventory.ShapelessRecipe implements IRecipe {

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

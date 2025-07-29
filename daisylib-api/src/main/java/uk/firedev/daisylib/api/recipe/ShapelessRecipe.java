package uk.firedev.daisylib.api.recipe;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShapelessRecipe extends AbstractRecipe<org.bukkit.inventory.ShapelessRecipe> {

    private final List<String> ingredients;
    private final NamespacedKey key;

    public ShapelessRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull ConfigurationSection section) {
        super(key, section, result);
        List<String> ingredients = section.getStringList("ingredients");
        if (ingredients.isEmpty()) {
            throw new RuntimeException("Shapeless recipe is missing ingredients.");
        }
        this.ingredients = ingredients;
        this.key = key;
    }

    @Override
    protected @NotNull org.bukkit.inventory.ShapelessRecipe prepareRecipe() {
        org.bukkit.inventory.ShapelessRecipe recipe = new org.bukkit.inventory.ShapelessRecipe(key, result);
        this.ingredients.forEach(ingredient -> {
            RecipeChoice choice = RecipeUtil.getRecipeChoice(ingredient);
            if (choice != null) {
                recipe.addIngredient(choice);
            }
        });
        return recipe;
    }

}

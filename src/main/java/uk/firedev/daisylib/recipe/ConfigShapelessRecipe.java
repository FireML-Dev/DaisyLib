package uk.firedev.daisylib.recipe;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * {@inheritDoc}
 */
public class ConfigShapelessRecipe extends AbstractConfigRecipe<ShapelessRecipe> {

    private final List<String> ingredients;
    private final NamespacedKey key;
    private final ItemStack result;

    public ConfigShapelessRecipe(@NonNull NamespacedKey key, @NonNull ItemStack result, @NonNull List<String> ingredients) {
        if (ingredients.isEmpty()) {
            throw new RuntimeException("Shapeless recipe is missing ingredients.");
        }
        this.ingredients = ingredients;
        this.key = key;
        this.result = result;
    }

    @Override
    protected @NonNull ShapelessRecipe prepareRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(key, result);
        this.ingredients.forEach(ingredient -> {
            RecipeChoice choice = RecipeUtil.RECIPE_CHOICE_FETCHER.apply(ingredient);
            if (choice != null) {
                recipe.addIngredient(choice);
            }
        });
        return recipe;
    }

}

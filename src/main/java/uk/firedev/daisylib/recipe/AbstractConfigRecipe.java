package uk.firedev.daisylib.recipe;

import org.bukkit.Bukkit;
import org.bukkit.inventory.CraftingRecipe;
import org.jspecify.annotations.NonNull;

/**
 * A custom recipe based on a {@link org.bukkit.configuration.ConfigurationSection}. Does not persist over server reloads.
 */
public abstract class AbstractConfigRecipe<R extends CraftingRecipe> {

    protected R recipe;

    protected AbstractConfigRecipe(@NonNull RecipeData data) {}

    public void register() {
        if (recipe == null) {
            this.recipe = prepareRecipe();
        }
        Bukkit.removeRecipe(recipe.getKey(), false);
        Bukkit.addRecipe(recipe);
    }

    public void unregister() {
        Bukkit.removeRecipe(recipe.getKey());
    }

    protected abstract @NonNull R prepareRecipe();

}

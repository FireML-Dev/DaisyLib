package uk.firedev.daisylib.recipe;

import org.bukkit.Bukkit;
import org.bukkit.inventory.CraftingRecipe;
import org.jspecify.annotations.NonNull;

/**
 * A custom recipe based on a {@link org.bukkit.configuration.ConfigurationSection}. Does not persist over server reloads.
 */
public abstract class AbstractConfigRecipe<R extends CraftingRecipe> {

    protected R recipe;

    protected AbstractConfigRecipe() {}

    public boolean isRegistered() {
        if (this.recipe == null) {
            return false;
        }
        return Bukkit.getRecipe(this.recipe.getKey()) != null;
    }

    public void register() {
        if (isRegistered()) {
            throw new RuntimeException("Attempted to register a recipe that is already registered.");
        }
        if (recipe == null) {
            this.recipe = prepareRecipe();
        }
        Bukkit.addRecipe(recipe);
    }

    public void unregister() {
        if (!isRegistered()) {
            throw new RuntimeException("Attempted to unregister a recipe that is not registered.");
        }
        Bukkit.removeRecipe(recipe.getKey());
    }

    protected abstract @NonNull R prepareRecipe();

}

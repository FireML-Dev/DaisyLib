package uk.firedev.daisylib.api.recipe;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractRecipe<R extends Recipe> {

    protected final NamespacedKey key;
    protected boolean registered = false;
    protected R recipe;

    protected AbstractRecipe(@NotNull NamespacedKey key) {
        this.key = key;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public void register() {
        if (isRegistered()) {
            throw new RuntimeException("Attempted to register a recipe that is already registered.");
        }
        if (recipe == null) {
            this.recipe = prepareRecipe();
        }
        Bukkit.addRecipe(recipe);
        this.registered = true;
    }

    public void unregister() {
        if (!isRegistered()) {
            throw new RuntimeException("Attempted to unregister a recipe that is not registered.");
        }
        Bukkit.removeRecipe(key, true);
        this.registered = false;
    }

    protected abstract @NotNull R prepareRecipe();

}

package uk.firedev.daisylib.api.crafting;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

public interface IRecipe extends Recipe {

    default boolean register() {
        return register(true);
    }

    /**
     * Registers the recipe
     * @param persistent Should this recipe persist after /minecraft:reload?
     * @return Whether the registration was successful or not.
     */
    default boolean register(boolean persistent) {
        if (isRegistered()) {
            return false;
        }
        if (Bukkit.addRecipe(this)) {
            setRegistered(true);
            if (persistent) {
                RecipeUtil.getInstance().addRecipe(this);
            }
            return true;
        }
        return false;
    }

    default boolean unregister() {
        if (!isRegistered()) {
            return false;
        }
        if (Bukkit.removeRecipe(getNamespacedKey())) {
            setRegistered(false);
            RecipeUtil.getInstance().removeRecipe(this);
            return true;
        }
        return false;
    }

    boolean isRegistered();

    void setRegistered(boolean registered);

    NamespacedKey getNamespacedKey();

    Plugin getPlugin();

}

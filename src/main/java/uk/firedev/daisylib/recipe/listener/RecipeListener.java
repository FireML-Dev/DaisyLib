package uk.firedev.daisylib.recipe.listener;

import io.papermc.paper.event.server.ServerResourcesReloadedEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;

import java.util.HashMap;
import java.util.Map;

public class RecipeListener implements Listener {

    private static final Map<NamespacedKey, Recipe> RECIPES = new HashMap<>();

    public static boolean addRecipe(@NotNull NamespacedKey key, @NotNull Recipe recipe) {
        if (RECIPES.containsKey(key)) {
            return false;
        }
        RECIPES.put(key, recipe);
        return true;
    }

    public static boolean removeRecipe(@NotNull NamespacedKey key) {
        return RECIPES.remove(key) != null;
    }

    @EventHandler
    public void onReload(ServerResourcesReloadedEvent event) {
        if (RECIPES.isEmpty()) {
            return;
        }
        Loggers.info(DaisyLibPlugin.getInstance().getLogger(), "Detected server reload. Reloading all custom recipes.");
        RECIPES.values().forEach(recipe -> Bukkit.addRecipe(recipe, true));
    }

}

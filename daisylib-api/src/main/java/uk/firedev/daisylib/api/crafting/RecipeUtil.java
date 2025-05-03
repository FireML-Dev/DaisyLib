package uk.firedev.daisylib.api.crafting;

import io.papermc.paper.event.server.ServerResourcesReloadedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class RecipeUtil implements Listener {

    private static RecipeUtil instance;

    private final List<IRecipe> recipeList;
    private boolean isListenerRegistered = false;

    private RecipeUtil() {
        recipeList = new ArrayList<>();
    }

    public static RecipeUtil getInstance() {
        if (instance == null) {
            instance = new RecipeUtil();
        }
        return instance;
    }

    @EventHandler
    public void onReload(ServerResourcesReloadedEvent event) {
        recipeList.forEach(iRecipe -> {
            // Already in the list, so don't persist
            iRecipe.setRegistered(false);
            iRecipe.register(false);
        });
    }

    public void addRecipe(@NotNull IRecipe recipe) {
        if (!isListenerRegistered) {
            // We use the recipe's plugin here, it shouldn't matter too much.
            Bukkit.getPluginManager().registerEvents(this, recipe.getPlugin());
            isListenerRegistered = true;
        }
        if (recipeList.contains(recipe)) {
            return;
        }
        recipeList.add(recipe);
    }

    public void removeRecipe(@NotNull IRecipe recipe) {
        recipeList.remove(recipe);
    }

    public static @NotNull RecipeChoice getRecipeChoiceFromItem(@NotNull ItemStack item) {
        if (item.isEmpty()) {
            return RecipeChoice.empty();
        } else {
            return new RecipeChoice.ExactChoice(item);
        }
    }

    public static @NotNull RecipeChoice getRecipeChoiceFromMaterial(@NotNull Material material) {
        if (material.isAir()) {
            return RecipeChoice.empty();
        } else {
            return new RecipeChoice.MaterialChoice(material);
        }
    }

    public static @NotNull RecipeChoice getRecipeChoiceFromMaterialName(@NotNull String name) {
        Material material = ItemUtils.getMaterial(name, Material.AIR);
        return getRecipeChoiceFromMaterial(material);
    }

    public static @Nullable RecipeChoice getRecipeChoiceFromItemNullable(@NotNull ItemStack item) {
        if (item.isEmpty()) {
            return null;
        } else {
            return RecipeUtil.getRecipeChoiceFromItem(item);
        }
    }

    public static @Nullable RecipeChoice getRecipeChoiceFromMaterialNullable(@NotNull Material material) {
        if (material.isAir()) {
            return null;
        } else {
            return new RecipeChoice.MaterialChoice(material);
        }
    }

    public static @Nullable RecipeChoice getRecipeChoiceFromMaterialNameNullable(@NotNull String name) {
        Material material = ItemUtils.getMaterial(name, Material.AIR);
        return getRecipeChoiceFromMaterialNullable(material);
    }

}

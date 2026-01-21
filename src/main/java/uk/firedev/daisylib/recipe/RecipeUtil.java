package uk.firedev.daisylib.recipe;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.recipe.types.CampfireRecipe;
import uk.firedev.daisylib.recipe.types.ShapedRecipe;
import uk.firedev.daisylib.recipe.types.ShapelessRecipe;
import uk.firedev.daisylib.recipe.types.StonecuttingRecipe;
import uk.firedev.daisylib.util.Utils;

import java.util.Map;

public class RecipeUtil {

    public static @Nullable RecipeChoice getRecipeChoice(String materialStr) {
        ItemStack item = Utils.getItem(materialStr);
        if (item == null) {
            return null;
        }
        if (item.isEmpty()) {
            return null;
        }
        return new RecipeChoice.ExactChoice(item);
    }

    /**
     * Resolves a recipe from the provided {@link ConfigurationSection} with the provided {@link NamespacedKey}.
     */
    public static @Nullable AbstractRecipe<?> getRecipe(@NotNull ConfigurationSection section, @NotNull NamespacedKey key, @NotNull ItemStack result) {
        String type = section.getString("type");
        if (type == null) {
            return null;
        }
        return switch (type.toLowerCase()) {
            case "shapeless" -> new ShapelessRecipe(
                key,
                result,
                section
            );
            case "shaped" -> new ShapedRecipe(
                key,
                result,
                section
            );
            case "campfire" -> new CampfireRecipe(
                key,
                result,
                section
            );
            case "stonecutting" -> new StonecuttingRecipe(
                key,
                result,
                section
            );
            default -> null; // Not a valid recipe type
        };
    }

    /**
     * Resolves a recipe and its key from the provided {@link ConfigurationSection}.
     */
    public static @Nullable AbstractRecipe<?> getRecipe(@NotNull ConfigurationSection section, @NotNull ItemStack result) {
        String keyStr = section.getString("key");
        if (keyStr == null) {
            return null;
        }
        NamespacedKey key = NamespacedKey.fromString(keyStr);
        if (key == null) {
            return null;
        }
        return getRecipe(section, key, result);
    }

}

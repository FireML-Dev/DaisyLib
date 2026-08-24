package uk.firedev.daisylib.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.utils.CommonUtils;

import java.util.List;
import java.util.function.Function;

public class RecipeUtil {

    public static @NonNull Function<@NonNull String, @Nullable RecipeChoice> RECIPE_CHOICE_FETCHER = s -> {
        Material material = CommonUtils.getMaterial(s);
        if (material == null || material.isAir()) {
            return null;
        }
        return new RecipeChoice.MaterialChoice(material);
    };

    public static @Nullable AbstractConfigRecipe<?> getRecipe(@NonNull ConfigurationSection section, @NonNull NamespacedKey key, @NonNull ItemStack result) {
        RecipeType type = CommonUtils.getEnumValue(RecipeType.class, section.getString("type"));
        if (type == null) {
            return null;
        }
        return switch (type) {
            case SHAPELESS -> {
                List<String> ingredients = section.getStringList("ingredients");
                yield new ConfigShapelessRecipe(
                    key,
                    result,
                    ingredients
                );
            }
            case SHAPED -> new ConfigShapedRecipe(
                key,
                result,
                section
            );
        };
    }

    public static boolean recipeExists(@NonNull NamespacedKey key) {
        return Bukkit.getRecipe(key) != null;
    }

    enum RecipeType {
        SHAPED,
        SHAPELESS;
    }

}

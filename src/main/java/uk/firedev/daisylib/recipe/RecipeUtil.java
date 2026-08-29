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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class RecipeUtil {

    public static @NonNull Function<@NonNull String, @Nullable RecipeChoice> RECIPE_CHOICE_FETCHER = s -> {
        Material material = CommonUtils.getMaterial(s);
        if (material == null || material.isAir()) {
            return null;
        }
        return new RecipeChoice.MaterialChoice(material);
    };
    private static final Map<String, Function<RecipeData, AbstractConfigRecipe<?>>> types = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    static {
        types.put("shapeless", ConfigShapelessRecipe::new);
        types.put("shaped", ConfigShapedRecipe::new);
    }

    public static @Nullable AbstractConfigRecipe<?> getRecipe(@NonNull ConfigurationSection section, @NonNull NamespacedKey key, @NonNull ItemStack result) {
        Function<RecipeData, AbstractConfigRecipe<?>> func = types.get(section.getString("type"));
        if (func == null) {
            return null;
        }
        RecipeData data = new RecipeData(key, result, section);
        return func.apply(data);
    }

    public static boolean recipeExists(@NonNull NamespacedKey key) {
        return Bukkit.getRecipe(key) != null;
    }

}

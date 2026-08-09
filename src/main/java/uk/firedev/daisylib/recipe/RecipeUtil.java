package uk.firedev.daisylib.recipe;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.addons.item.ItemAddonRegistry;
import uk.firedev.daisylib.utils.CommonUtils;

import java.util.List;
import java.util.function.Function;

public class RecipeUtil {

    public static @NonNull Function<@NonNull String, @Nullable RecipeChoice> RECIPE_CHOICE_FETCHER = s -> {
        Material material = CommonUtils.getMaterial(s);
        if (material != null) {
            return new RecipeChoice.MaterialChoice(material);
        }
        ItemStack item = ItemAddonRegistry.get().processString(s);
        if (item == null || item.isEmpty()) {
            return null;
        }
        return new RecipeChoice.ExactChoice(item);
    };

    public static @Nullable AbstractConfigRecipe<?> getRecipe(@NonNull ConfigurationSection section, @NonNull NamespacedKey key, @NonNull ItemStack result) {
        String type = section.getString("type");
        if (type == null) {
            return null;
        }
        return switch (type.toLowerCase()) {
            case "shapeless" -> {
                List<String> ingredients = section.getStringList("ingredients");
                yield new ConfigShapelessRecipe(
                    key,
                    result,
                    ingredients
                );
            }
            case "shaped" -> new ConfigShapedRecipe(
                key,
                result,
                section
            );
            default -> null; // Not a valid recipe type
        };
    }

}

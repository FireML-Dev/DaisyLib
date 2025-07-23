package uk.firedev.daisylib.api.recipe;

import com.oheers.fish.FishUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.utils.ItemUtils;

import java.util.List;

public class RecipeUtil {

    public static @Nullable RecipeChoice getRecipeChoice(String materialStr) {
        ItemStack item = ItemUtils.getItem(materialStr);
        if (item == null || item.isEmpty()) {
            return null;
        }
        return new RecipeChoice.ExactChoice(item);
    }

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
            default -> null; // Not a valid recipe type
        };
    }

}

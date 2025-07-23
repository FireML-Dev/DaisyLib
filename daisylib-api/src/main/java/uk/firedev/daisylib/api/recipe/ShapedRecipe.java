package uk.firedev.daisylib.api.recipe;

import com.oheers.fish.FishUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.utils.ObjectUtils;

import java.util.List;

public class ShapedRecipe extends AbstractRecipe<org.bukkit.inventory.ShapedRecipe> {

    private final List<String> rawShape;
    private final ConfigurationSection ingredientsSection;
    private final NamespacedKey key;
    private final ItemStack result;

    public ShapedRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull ConfigurationSection section) {
        this.rawShape = section.getStringList("shape");
        this.ingredientsSection = section.getConfigurationSection("ingredients");
        if (this.rawShape.isEmpty() || this.ingredientsSection == null) {
            throw new RuntimeException("Shaped recipe is missing shape or ingredients.");
        }
        this.key = key;
        this.result = result;
    }

    @Override
    protected @NotNull org.bukkit.inventory.ShapedRecipe prepareRecipe() {
        org.bukkit.inventory.ShapedRecipe recipe = new org.bukkit.inventory.ShapedRecipe(key, result);

        String[] shape = rawShape.stream().limit(3).toArray(String[]::new);
        recipe.shape(shape);

        ingredientsSection.getKeys(false).forEach(key -> {
            char character = ObjectUtils.getCharFromString(key, '#');
            String materialStr = ingredientsSection.getString(key);

            if (materialStr == null) {
                // If invalid material string, just skip it.
                return;
            }

            RecipeChoice choice = RecipeUtil.getRecipeChoice(materialStr);
            if (choice == null) {
                // If a recipe choice could not be created, just skip it.
                return;
            }
            recipe.setIngredient(character, choice);
        });

        return recipe;
    }

}

package uk.firedev.daisylib.recipe;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.utils.CommonUtils;

import java.util.List;

public class ConfigShapedRecipe extends AbstractConfigRecipe<ShapedRecipe> {

    private final List<String> rawShape;
    private final ConfigurationSection ingredientsSection;
    private final NamespacedKey key;
    private final ItemStack result;

    public ConfigShapedRecipe(@NonNull NamespacedKey key, @NonNull ItemStack result, @NonNull ConfigurationSection section) {
        this.rawShape = section.getStringList("shape");
        this.ingredientsSection = section.getConfigurationSection("ingredients");
        if (this.rawShape.isEmpty() || this.ingredientsSection == null) {
            throw new RuntimeException("Shaped recipe is missing shape or ingredients.");
        }
        this.key = key;
        this.result = result;
    }

    @Override
    protected @NonNull ShapedRecipe prepareRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(key, result);

        String[] shape = rawShape.stream().limit(3).toArray(String[]::new);
        recipe.shape(shape);

        ingredientsSection.getKeys(false).forEach(key -> {
            char character = CommonUtils.getCharFromString(key, '#');
            String materialStr = ingredientsSection.getString(key);
            if (materialStr == null) {
                return;
            }
            RecipeChoice choice = RecipeUtil.RECIPE_CHOICE_FETCHER.apply(materialStr);
            if (choice == null) {
                return;
            }
            recipe.setIngredient(character, choice);
        });

        return recipe;
    }

}

package uk.firedev.daisylib.recipe.types;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.recipe.AbstractRecipe;
import uk.firedev.daisylib.recipe.RecipeUtil;

public class StonecuttingRecipe extends AbstractRecipe<org.bukkit.inventory.StonecuttingRecipe> {

    private final RecipeChoice input;
    private final NamespacedKey key;

    public StonecuttingRecipe(@NonNull NamespacedKey key, @NonNull ItemStack result, @NonNull ConfigurationSection section) {
        super(key, section, result);
        this.input = RecipeUtil.getRecipeChoice(section.getString("input"));
        if (input == null) {
            throw new RuntimeException("Stonecutting recipe has a missing or invalid input ingredient.");
        }
        this.key = key;
    }

    @Override
    protected org.bukkit.inventory.@NonNull StonecuttingRecipe prepareRecipe() {
        return new org.bukkit.inventory.StonecuttingRecipe(
            key,
            result,
            input
        );
    }

}

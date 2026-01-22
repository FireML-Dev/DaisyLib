package uk.firedev.daisylib.recipe.types;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.recipe.AbstractRecipe;
import uk.firedev.daisylib.recipe.RecipeUtil;

public class CampfireRecipe extends AbstractRecipe<org.bukkit.inventory.CampfireRecipe> {

    private final RecipeChoice input;
    private final NamespacedKey key;
    private final ItemStack result;
    private final float experience;
    private final int cookingTime;

    public CampfireRecipe(@NonNull NamespacedKey key, @NonNull ItemStack result, @NonNull ConfigurationSection section) {
        super(key, section, result);
        this.input = RecipeUtil.getRecipeChoice(section.getString("input"));
        if (input == null) {
            throw new RuntimeException("Campfire recipe has a missing or invalid input ingredient.");
        }
        this.key = key;
        this.result = result;
        this.experience = (float) section.getDouble("experience", 0);
        this.cookingTime = section.getInt("cooking-time", 30) * 20; // Configuration will ask for seconds, we need ticks
    }

    @Override
    protected org.bukkit.inventory.@NonNull CampfireRecipe prepareRecipe() {
        return new org.bukkit.inventory.CampfireRecipe(
            key,
            result,
            input,
            experience,
            cookingTime
        );
    }

}

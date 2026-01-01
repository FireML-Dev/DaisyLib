package uk.firedev.daisylib.builders.dialog.recipe;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.builders.dialog.recipe.abstracted.RecipeDialogBuilder;

public class ShapelessRecipeDialogBuilder implements RecipeDialogBuilder {

    private @NotNull Component title;
    private final @NotNull Recipe recipe;

    public ShapelessRecipeDialogBuilder(@NotNull ShapedRecipe recipe) {
        this.recipe = recipe;
        this.title = Component.text("Shapeless Recipe: " + recipe.getKey().asString());
    }

}

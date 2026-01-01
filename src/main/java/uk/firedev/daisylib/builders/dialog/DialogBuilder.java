package uk.firedev.daisylib.builders.dialog;

import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.builders.dialog.recipe.ShapedRecipeDialogBuilder;
import uk.firedev.daisylib.builders.dialog.recipe.ShapelessRecipeDialogBuilder;
import uk.firedev.daisylib.builders.dialog.recipe.abstracted.RecipeDialogBuilder;

public class DialogBuilder {

    private DialogBuilder() {}

    public static InformationDialogBuilder information() {
        return new InformationDialogBuilder();
    }

    public static @Nullable RecipeDialogBuilder recipe(@NotNull Recipe recipe) {
        return switch (recipe) {
            case ShapedRecipe shaped -> shapedRecipe(shaped);
            case ShapelessRecipe shapeless -> shapelessRecipe(shapeless);
            default -> null;
        };
    }

    public static ShapedRecipeDialogBuilder shapedRecipe(@NotNull ShapedRecipe recipe) {
        return new ShapedRecipeDialogBuilder(recipe);
    }

    public static ShapelessRecipeDialogBuilder shapelessRecipe(@NotNull ShapelessRecipe recipe) {
        return new ShapelessRecipeDialogBuilder(recipe);
    }

}

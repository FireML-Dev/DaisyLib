package uk.firedev.daisylib.recipe.choice;

import org.bukkit.inventory.RecipeChoice;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface RecipeChoiceWrapper {

    @Nullable RecipeChoice parse(@NonNull String string);

}

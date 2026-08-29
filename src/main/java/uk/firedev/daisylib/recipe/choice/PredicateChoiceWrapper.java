package uk.firedev.daisylib.recipe.choice;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.utils.CommonUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * Reflective wrapper for RecipeChoice.PredicateChoice.
 * <p>
 * Make sure you call {@link uk.firedev.daisylib.recipe.RecipeUtil#registerRecipeChoice(RecipeChoiceWrapper)} if using this.
 */
public abstract class PredicateChoiceWrapper implements RecipeChoiceWrapper {

    private static final @Nullable Method PREDICATE_FACTORY = CommonUtils.getMethodOrNull(
        RecipeChoice.class,
        "predicateChoice",
        Predicate.class, ItemStack.class
    );

    /**
     * @return The item to represent this predicate choice in the recipe book. Cannot be empty or air.
     */
    public abstract @NonNull ItemStack getExampleItem(@NonNull String string);

    public abstract @Nullable Predicate<@NonNull ItemStack> createPredicate(@NonNull String string);

    @Override
    public @Nullable RecipeChoice parse(@NonNull String string) {
        if (PREDICATE_FACTORY == null) {
            return null;
        }
        Predicate<ItemStack> predicate = createPredicate(string);
        if (predicate == null) {
            return null;
        }
        try {
            return (RecipeChoice) PREDICATE_FACTORY.invoke(null, predicate, getExampleItem(string));
        } catch (InvocationTargetException | IllegalAccessException | ClassCastException e) {
            DaisyLib.get().getLogging().exception(e);
            return null;
        }
    }

}

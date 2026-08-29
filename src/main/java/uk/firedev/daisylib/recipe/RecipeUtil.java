package uk.firedev.daisylib.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.recipe.choice.MaterialChoiceWrapper;
import uk.firedev.daisylib.recipe.choice.RecipeChoiceWrapper;
import uk.firedev.daisylib.utils.CommonUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class RecipeUtil {

    private static final Map<String, Function<RecipeData, AbstractConfigRecipe<?>>> types = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static final List<RecipeChoiceWrapper> recipeChoices = new ArrayList<>();

    static {
        types.put("shapeless", ConfigShapelessRecipe::new);
        types.put("shaped", ConfigShapedRecipe::new);
        // MaterialChoice is the only one we can handle by default. ExactChoice and PredicateChoice need to be added by the parent plugin.
        recipeChoices.add(new MaterialChoiceWrapper());
    }

    public static @Nullable AbstractConfigRecipe<?> getRecipe(@NonNull ConfigurationSection section, @NonNull NamespacedKey key, @NonNull ItemStack result) {
        Function<RecipeData, AbstractConfigRecipe<?>> func = types.get(section.getString("type"));
        if (func == null) {
            return null;
        }
        RecipeData data = new RecipeData(key, result, section);
        return func.apply(data);
    }

    public static @Nullable RecipeChoice getRecipeChoice(@NonNull String string) {
        for (RecipeChoiceWrapper wrapper : recipeChoices) {
            RecipeChoice choice = wrapper.parse(string);
            if (choice != null) {
                return choice;
            }
        }
        DaisyLib.get().getLogging().debug("Could not get a valid RecipeChoice for " + string);
        return null;
    }

    public static boolean recipeExists(@NonNull NamespacedKey key) {
        return Bukkit.getRecipe(key) != null;
    }

    public static void registerRecipeChoice(@NonNull RecipeChoiceWrapper wrapper) {
        if (recipeChoices.contains(wrapper)) {
            return;
        }
        recipeChoices.add(wrapper);
    }

    public static void registerRecipeType(@NonNull String name, @NonNull Function<@NonNull RecipeData, @NonNull AbstractConfigRecipe<?>> func) {
        if (types.containsKey(name)) {
            DaisyLib.get().getLogging().warn("Overwriting " + name + " recipe type.", new Throwable());
        }
        types.put(name, func);
    }

}

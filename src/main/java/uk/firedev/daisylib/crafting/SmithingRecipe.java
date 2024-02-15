package uk.firedev.daisylib.crafting;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingTransformRecipe;
import org.bukkit.inventory.SmithingTrimRecipe;
import org.jetbrains.annotations.NotNull;

public class SmithingRecipe extends SmithingTransformRecipe {

    private boolean registered = false;
    private final NamespacedKey key;

    public SmithingRecipe(@NotNull NamespacedKey key, @NotNull ItemStack template, @NotNull ItemStack base, @NotNull ItemStack addition, @NotNull ItemStack result) {
        super(key, result, new RecipeChoice.ExactChoice(template), new RecipeChoice.ExactChoice(base), new RecipeChoice.ExactChoice(addition));
        this.key = key;
    }

    public SmithingRecipe(@NotNull NamespacedKey key, @NotNull RecipeChoice template, @NotNull RecipeChoice base, @NotNull RecipeChoice addition, @NotNull ItemStack result) {
        super(key, result, template, base, addition);
        this.key = key;
    }

    public boolean register() {
        if (registered) {
            return false;
        }
        if (Bukkit.addRecipe(this)) {
            registered = true;
            return true;
        }
        return false;
    }

    public boolean unregister() {
        if (!registered) {
            return false;
        }
        if (Bukkit.removeRecipe(this.key, true)) {
            registered = false;
            return true;
        }
        return false;
    }

}

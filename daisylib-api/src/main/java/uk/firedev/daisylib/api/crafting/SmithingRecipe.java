package uk.firedev.daisylib.api.crafting;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SmithingRecipe extends org.bukkit.inventory.SmithingTransformRecipe implements IRecipe {

    private final Plugin plugin;
    private boolean registered = false;
    private final NamespacedKey key;

    public SmithingRecipe(@NotNull Plugin plugin, @NotNull NamespacedKey key, @NotNull ItemStack template, @NotNull ItemStack base, @NotNull ItemStack addition, @NotNull ItemStack result) {
        super(key, result, RecipeUtil.getRecipeChoiceFromItem(template), RecipeUtil.getRecipeChoiceFromItem(base), RecipeUtil.getRecipeChoiceFromItem(addition));
        this.plugin = plugin;
        this.key = key;
    }

    public SmithingRecipe(@NotNull Plugin plugin, @NotNull NamespacedKey key, @NotNull RecipeChoice template, @NotNull RecipeChoice base, @NotNull RecipeChoice addition, @NotNull ItemStack result) {
        super(key, result, template, base, addition);
        this.plugin = plugin;
        this.key = key;
    }

    @Override
    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Override
    public NamespacedKey getNamespacedKey() {
        return key;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

}

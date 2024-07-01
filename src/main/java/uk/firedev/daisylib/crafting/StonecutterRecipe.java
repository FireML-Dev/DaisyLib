package uk.firedev.daisylib.crafting;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StonecutterRecipe extends org.bukkit.inventory.StonecuttingRecipe implements IRecipe {

    private boolean registered = false;
    private final NamespacedKey key;

    public StonecutterRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull Material source) {
        super(key, result, source);
        this.key = key;
    }

    public StonecutterRecipe(@NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull ItemStack source) {
        super(key, result, RecipeUtil.getRecipeChoiceFromItem(source));
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

}

package uk.firedev.daisylib.api.crafting;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class StonecutterRecipe extends org.bukkit.inventory.StonecuttingRecipe implements IRecipe {

    private final Plugin plugin;
    private boolean registered = false;
    private final NamespacedKey key;

    public StonecutterRecipe(@NotNull Plugin plugin, @NotNull NamespacedKey key, @NotNull Material source, @NotNull ItemStack result) {
        super(key, result, source);
        this.plugin = plugin;
        this.key = key;
    }

    public StonecutterRecipe(@NotNull Plugin plugin, @NotNull NamespacedKey key, @NotNull ItemStack source, @NotNull ItemStack result) {
        super(key, result, RecipeUtil.getRecipeChoiceFromItem(source));
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

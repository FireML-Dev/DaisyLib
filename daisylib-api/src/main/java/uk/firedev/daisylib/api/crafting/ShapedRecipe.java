package uk.firedev.daisylib.api.crafting;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShapedRecipe extends org.bukkit.inventory.ShapedRecipe implements IRecipe {

    private final Plugin plugin;
    private final NamespacedKey key;
    private final List<ItemStack> recipeItems;
    private boolean registered = false;

    public ShapedRecipe(@NotNull Plugin plugin, @NotNull NamespacedKey key, @NotNull ItemStack result, @NotNull List<ItemStack> recipeItems) {
        super(key, result);
        this.plugin = plugin;
        this.key = key;
        if (recipeItems.size() > 9) {
            this.recipeItems = recipeItems.subList(0, 8);
        } else {
            this.recipeItems = recipeItems;
        }
        buildRecipe();
    }

    private void buildRecipe() {
        shape("ABC", "DEF", "GHI");
        for (int i = 0; i < 9; ++i) {
            ItemStack item;
            try {
                item = recipeItems.get(i);
            } catch (IndexOutOfBoundsException ex) {
                item = ItemStack.empty();
            }
            char loopChar = charFromInt(i);
            RecipeChoice choice = RecipeUtil.getRecipeChoiceFromItemNullable(item);
            if (choice != null) {
                setIngredient(loopChar, choice);
            }
        }
    }

    private char charFromInt(int i) {
        if (i > 8 || i < 0) {
            return 'X';
        }
        char[] charMap = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        return charMap[i];
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

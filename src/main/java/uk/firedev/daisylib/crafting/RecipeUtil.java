package uk.firedev.daisylib.crafting;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.utils.ItemUtils;

public class RecipeUtil {

    public static RecipeChoice getRecipeChoiceFromItem(@NotNull ItemStack item) {
        if (item.getType().isAir()) {
            return RecipeChoice.empty();
        } else {
            return RecipeUtil.getRecipeChoiceFromItem(item);
        }
    }

    public static RecipeChoice getRecipeChoiceFromMaterial(@NotNull Material material) {
        if (material.isAir()) {
            return RecipeChoice.empty();
        } else {
            return new RecipeChoice.MaterialChoice(material);
        }
    }

    public static RecipeChoice getRecipeChoiceFromMaterialName(@NotNull String name) {
        Material material = ItemUtils.getMaterial(name, Material.AIR);
        return getRecipeChoiceFromMaterial(material);
    }

}

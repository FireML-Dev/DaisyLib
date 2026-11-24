package uk.firedev.daisylib.recipe.choice;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.item.CustomItem;
import uk.firedev.daisylib.registry.Registry;

public class CustomItemChoiceWrapper implements RecipeChoiceWrapper {

    @Override
    public @Nullable RecipeChoice parse(@NonNull String string) {
        if (!string.contains(":")) {
            return null;
        }
        String[] split = string.split(":", 2);
        String prefix = split[0];
        String id = split[1];
        CustomItem custom = Registry.CUSTOM_ITEM.get(prefix);
        if (custom == null) {
            return null;
        }
        ItemStack example = custom.getItemStack(id);
        if (example == null) {
            return null;
        }
        return RecipeChoice.predicateChoice(
            item -> custom.isMatching(item, id),
            example
        );
    }

}

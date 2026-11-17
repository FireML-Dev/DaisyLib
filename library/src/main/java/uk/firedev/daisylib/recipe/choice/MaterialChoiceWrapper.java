package uk.firedev.daisylib.recipe.choice;

import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.utils.CommonUtils;

@ApiStatus.Internal
public final class MaterialChoiceWrapper implements RecipeChoiceWrapper {

    @Override
    public @Nullable RecipeChoice parse(@NonNull String string) {
        Material material = CommonUtils.getMaterial(string);
        return material == null || material.isAir() ? null : new RecipeChoice.MaterialChoice(material);
    }

}

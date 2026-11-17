package uk.firedev.daisylib.recipe;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;

public record RecipeData(@NonNull NamespacedKey key, @NonNull ItemStack result, @NonNull ConfigurationSection section) {

    @ApiStatus.Internal
    public RecipeData {}

}

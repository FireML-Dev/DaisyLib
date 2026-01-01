package uk.firedev.daisylib.builders.dialog.recipe;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.dialog.DialogLike;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.builders.dialog.recipe.abstracted.RecipeDialogBuilder;
import uk.firedev.messagelib.message.ComponentMessage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class ShapedRecipeDialogBuilder implements RecipeDialogBuilder {

    private @NotNull Component title;
    private final @NotNull ShapedRecipe recipe;

    public ShapedRecipeDialogBuilder(@NotNull ShapedRecipe recipe) {
        this.recipe = recipe;
        this.title = Component.text("Shaped Recipe: " + recipe.getKey().asString());
    }

    public ShapedRecipeDialogBuilder withTitle(@NotNull Object title) {
        this.title = ComponentMessage.componentMessage(title).get();
        return this;
    }

    public DialogLike build() {
        return Dialog.create(builder -> builder.empty()
            .base(
                DialogBase.builder(title)
                    .canCloseWithEscape(true)
                    .afterAction(DialogBase.DialogAfterAction.CLOSE)
                    .body(getBodies())
                    .build()
            )
            .type(DialogType.notice(
                ActionButton.builder(Component.text("Exit")).build()
            ))
        );
    }

    private List<? extends DialogBody> getBodies() {
        final Map<Character, ItemStack> stackMap = new HashMap<>();
        recipe.getChoiceMap().forEach((character, recipeChoice) -> {
            switch (recipeChoice) {
                case RecipeChoice.ExactChoice exact -> stackMap.put(character, exact.getItemStack());
                case RecipeChoice.MaterialChoice material -> stackMap.put(character, material.getItemStack());
                default -> {}
            }
        });
        System.out.println(Arrays.toString(recipe.getShape()));
        return List.of();
    }

}

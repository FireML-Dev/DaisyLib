package uk.firedev.daisylib.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.builders.ItemBuilder;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.message.component.ComponentReplacer;
import uk.firedev.daisylib.api.message.string.StringMessage;
import uk.firedev.daisylib.api.message.string.StringReplacer;

import java.util.List;

public class GUIUtils {

    public static String getFormattedTitle(@NotNull String title, @Nullable StringReplacer replacer) {
        Component parsed = StringMessage.of(title).applyReplacer(replacer).toComponentMessage().getMessage();
        return LegacyComponentSerializer.legacySection().serialize(parsed);
    }

    public static String getFormattedTitle(@NotNull Component title, @Nullable ComponentReplacer replacer) {
        Component parsed = ComponentMessage.of(title).applyReplacer(replacer).getMessage();
        return LegacyComponentSerializer.legacySection().serialize(parsed);
    }

    public static ItemStack createItemStack(@NotNull String materialName, @NotNull Material defaultMaterial, @NotNull String display, @NotNull List<String> lore) {
        return ItemBuilder.itemBuilder(materialName, defaultMaterial)
                .withStringDisplay(display, null)
                .withStringLore(lore, null)
                .getItem();
    }

}

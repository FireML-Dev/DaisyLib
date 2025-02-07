package uk.firedev.daisylib.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.builders.ItemBuilder;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.message.component.ComponentReplacer;
import uk.firedev.daisylib.api.message.string.StringMessage;
import uk.firedev.daisylib.api.message.string.StringReplacer;
import uk.firedev.daisylib.api.utils.ItemUtils;

import java.util.List;

public class GuiUtils {

    public static String getFormattedTitle(@NotNull String title, @Nullable StringReplacer replacer) {
        Component parsed = StringMessage.of(title).applyReplacer(replacer).toComponentMessage().getMessage();
        return LegacyComponentSerializer.legacySection().serialize(parsed);
    }

    public static String getFormattedTitle(@NotNull Component title, @Nullable ComponentReplacer replacer) {
        Component parsed = ComponentMessage.of(title).applyReplacer(replacer).getMessage();
        return LegacyComponentSerializer.legacySection().serialize(parsed);
    }

    public static ItemStack createItemStack(@NotNull String materialName, @NotNull Material defaultMaterial, @NotNull String display, @NotNull List<String> lore) {
        return ItemBuilder.create(materialName, defaultMaterial)
                .withStringDisplay(display, null)
                .withStringLore(lore, null)
                .getItem();
    }

    public static ItemStack createFiller(@NotNull String materialName, @NotNull Material defaultMaterial) {
        Material material = ItemUtils.getMaterial(materialName, defaultMaterial);
        ItemStack item = ItemStack.of(material);
        item.editMeta(meta -> {
            meta.customName(Component.empty());
            meta.setHideTooltip(true);
        });
        return item;
    }

}

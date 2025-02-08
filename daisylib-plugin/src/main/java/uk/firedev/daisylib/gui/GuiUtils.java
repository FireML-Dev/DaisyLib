package uk.firedev.daisylib.gui;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.util.GuiFiller;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.builders.ItemBuilder;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.utils.ItemUtils;
import uk.firedev.daisylib.api.utils.ObjectUtils;

import java.util.List;

public class GuiUtils {

    public static Gui createGui(@NotNull ConfigurationSection config) {
        GuiType type = ObjectUtils.getEnumValue(
            GuiType.class,
            config.getString("type", "CHEST")
        );
        if (type == null) {
            type = GuiType.CHEST;
        }

        ComponentMessage title = ComponentMessage.fromConfig(config, "title", "Gui");

        // Create the Gui
        Gui gui = Gui.gui()
            .disableAllInteractions()
            .title(title.getMessage())
            .type(type)
            .rows(config.getInt("rows", 6))
            .create();

        // Load configured items
        loadItems(gui, config);

        // Load filler
        loadFiller(gui, config);

        return gui;
    }

    public static void loadItems(@NotNull BaseGui gui, @NotNull ConfigurationSection guiConfig) {
        ConfigurationSection itemSection = guiConfig.getConfigurationSection("items");
        if (itemSection == null) {
            return;
        }
        itemSection.getKeys(false).forEach(key -> {
            ConfigurationSection section = itemSection.getConfigurationSection(key);
            if (section == null) {
                return;
            }
            addGuiItem(gui, section);
        });
    }

    public static void addGuiItem(@NotNull BaseGui gui, @NotNull ConfigurationSection itemSection) {
        ItemStack item = ItemBuilder.create(Material.AIR)
            .loadConfig(itemSection, null, null)
            .getItem();
        if (item.getType() == Material.AIR) {
            return;
        }
        int row = itemSection.getInt("row", -1);
        int column = itemSection.getInt("column", -1);
        GuiItem guiItem = new GuiItem(item);
        gui.setItem(row, column, guiItem);
    }

    // TODO testing
    public static void loadFiller(@NotNull BaseGui gui, @NotNull ConfigurationSection guiConfig) {
        ConfigurationSection fillerSection = guiConfig.getConfigurationSection("filler");
        if (fillerSection == null) {
            return;
        }

        // Prepare Enum
        FillerType fillerType = ObjectUtils.getEnumValue(
            FillerType.class,
            fillerSection.getString("type")
        );
        if (fillerType == null) {
            return;
        }

        // Prepare the filler item
        ItemStack fillerItem = ItemStack.of(
            ItemUtils.getMaterial(fillerSection.getString("material"), Material.AIR)
        );
        fillerItem.editMeta(meta -> {
            meta.customName(Component.empty());
            meta.setHideTooltip(true);
        });

        // Handle filler
        GuiItem item = new GuiItem(fillerItem);
        GuiFiller filler = gui.getFiller();

        switch (fillerType) {
            case ALL -> filler.fill(item);
            case BORDER -> filler.fillBorder(item);
            case SIDE -> {
                GuiFiller.Side side = ObjectUtils.getEnumValue(
                    GuiFiller.Side.class,
                    fillerSection.getString("side")
                );
                if (side == null) {
                    return;
                }
                filler.fillSide(side, List.of(item));
            }
            case BETWEEN -> {
                int rowFrom = fillerSection.getInt("between-points.rowFrom", -1);
                int columnFrom = fillerSection.getInt("between-points.columnFrom", -1);
                int rowTo = fillerSection.getInt("between-points.rowTo", -1);
                int columnTo = fillerSection.getInt("between-points.columnTo", -1);
                if (rowFrom == -1 || columnFrom == -1 || rowTo == -1 || columnTo == -1) {
                    return;
                }
                filler.fillBetweenPoints(rowFrom, columnFrom, rowTo, columnTo, item);
            }
        }
    }

}

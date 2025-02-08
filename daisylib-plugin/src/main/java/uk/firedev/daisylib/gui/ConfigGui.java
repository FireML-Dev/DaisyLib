package uk.firedev.daisylib.gui;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.util.GuiFiller;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.builders.ItemBuilder;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.utils.ItemUtils;
import uk.firedev.daisylib.api.utils.ObjectUtils;

import java.util.List;

public class ConfigGui {

    private final BaseGui gui;
    private final ConfigurationSection config;
    private final Player player;

    public ConfigGui(@NotNull ConfigurationSection config, @NotNull Player player) {
        this.config = config;
        this.player = player;

        GuiType type = ObjectUtils.getEnumValue(
            GuiType.class,
            config.getString("type", "CHEST")
        );
        if (type == null) {
            type = GuiType.CHEST;
        }

        ComponentMessage title = ComponentMessage.fromConfig(config, "title", "Gui");

        // Create the Gui
        this.gui = Gui.gui()
            .disableAllInteractions()
            .title(title.getMessage())
            .type(type)
            .rows(config.getInt("rows", 6))
            .create();

        // Load configured items
        loadItems();

        // Load filler
        loadFiller();
    }

    public BaseGui getGui() {
        return this.gui;
    }

    public void open() {
        this.gui.open(this.player);
    }

    // Loading Things

    private void loadItems() {
        ConfigurationSection itemSection = this.config.getConfigurationSection("items");
        if (itemSection == null) {
            return;
        }
        itemSection.getKeys(false).forEach(key -> {
            ConfigurationSection section = itemSection.getConfigurationSection(key);
            if (section == null) {
                return;
            }
            addGuiItem(section);
        });
    }

    private void addGuiItem(@NotNull ConfigurationSection itemSection) {
        ItemStack item = ItemBuilder.createWithConfig(itemSection, null, null).getItem();
        if (item.getType() == Material.AIR) {
            return;
        }
        GuiItem guiItem = new GuiItem(item);
        // Put the item in all of its configured locations
        itemSection.getStringList("locations").forEach(location -> {
            String[] splitLocation = location.split(",", 2);
            String columnStr = ObjectUtils.getOrDefault(splitLocation, 0, null);
            String rowStr = ObjectUtils.getOrDefault(splitLocation, 1, null);
            int column = ObjectUtils.getIntOrDefault(columnStr, -1);
            int row = ObjectUtils.getIntOrDefault(rowStr, -1);
            this.gui.setItem(row, column, guiItem);
        });
    }

    private void loadFiller() {
        ConfigurationSection fillerSection = this.config.getConfigurationSection("filler");
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
        GuiFiller filler = this.gui.getFiller();

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

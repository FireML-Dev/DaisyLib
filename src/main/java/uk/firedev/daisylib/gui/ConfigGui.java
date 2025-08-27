package uk.firedev.daisylib.gui;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.util.GuiFiller;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.builders.ItemBuilder;
import uk.firedev.daisylib.utils.ItemUtils;
import uk.firedev.daisylib.utils.ObjectUtils;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.messagelib.config.PaperConfigLoader;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

public class ConfigGui {

    protected final TreeMap<String, Consumer<InventoryClickEvent>> actions = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    protected final ConfigurationSection config;
    protected final Player player;

    protected BaseGui gui;

    public ConfigGui(@Nullable ConfigurationSection config, @NotNull Player player) {
        actions.put("close", event -> event.getWhoClicked().closeInventory());

        this.config = config;
        this.player = player;

        if (config == null) {
            this.gui = Gui.gui()
                .disableAllInteractions()
                .rows(6)
                .create();
        }
    }

    public BaseGui getGui() {
        if (this.gui == null) {
            this.gui = createGui();
        }
        return this.gui;
    }

    public ConfigurationSection getGuiConfig() {
        return config;
    }

    public void open() {
        getGui().open(this.player);
    }

    public void addActions(@NotNull Map<String, Consumer<InventoryClickEvent>> actions) {
        actions.forEach(this::addAction);
    }

    public void addAction(@NotNull String name, @NotNull Consumer<InventoryClickEvent> action) {
        this.actions.putIfAbsent(name, action);
    }

    // Loading Things

    protected BaseGui createGui() {
        GuiType type = ObjectUtils.getEnumValue(
            GuiType.class,
            config.getString("type", "CHEST")
        );
        if (type == null) {
            type = GuiType.CHEST;
        }

        PaperConfigLoader loader = new PaperConfigLoader(config);
        ComponentMessage title = ComponentMessage.componentMessage(loader, "title");
        if (title == null) {
            title = ComponentMessage.componentMessage(Component.text("Gui"));
        }

        ComponentSingleMessage singleMessage = ComponentMessage.componentSingleMessage(title);

        BaseGui gui = Gui.gui()
            .disableAllInteractions()
            .title(singleMessage.get())
            .type(type)
            .rows(config.getInt("rows", 6))
            .create();

        // Load filler
        loadFiller(gui);

        // Load configured items
        loadItems(gui);

        return gui;
    }

    protected void loadItems(@NotNull BaseGui gui) {
        ConfigurationSection itemSection = this.config.getConfigurationSection("items");
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

    protected void addGuiItem(@NotNull BaseGui gui, @NotNull ConfigurationSection itemSection) {
        ItemStack item = ItemBuilder.createWithConfig(itemSection, null, null).getItem();
        if (item.isEmpty()) {
            return;
        }
        GuiItem guiItem;
        ConfigurationSection actionSection = itemSection.getConfigurationSection("click-actions");
        if (actionSection != null) {
            guiItem = new GuiItem(item, event -> {
                Consumer<InventoryClickEvent> action = switch (event.getClick()) {
                    case LEFT -> actions.get(actionSection.getString("left", ""));
                    case RIGHT -> actions.get(actionSection.getString("right", ""));
                    case MIDDLE -> actions.get(actionSection.getString("middle", ""));
                    case DROP -> actions.get(actionSection.getString("drop", ""));
                    default -> null;
                };
                if (action == null) {
                    event.setCancelled(true);
                    return;
                }
                action.accept(event);
            });
        } else {
            guiItem = new GuiItem(item, event -> event.setCancelled(true));
        }
        // Put the item in all of its configured locations
        itemSection.getStringList("locations").forEach(location -> {
            String[] splitLocation = location.split(",", 2);
            String columnStr = ObjectUtils.getOrDefault(splitLocation, 0, null);
            String rowStr = ObjectUtils.getOrDefault(splitLocation, 1, null);
            int column = ObjectUtils.getIntOrDefault(columnStr, -1);
            int row = ObjectUtils.getIntOrDefault(rowStr, -1);
            gui.setItem(row, column, guiItem);
        });
    }

    protected void loadFiller(@NotNull BaseGui gui) {
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
        ItemType itemType = ItemUtils.getItemType(
            fillerSection.getString("material")
        );
        if (itemType == null || itemType == ItemType.AIR) {
            return;
        }
        ItemStack fillerItem = itemType.createItemStack();
        fillerItem.editMeta(meta -> {
            meta.customName(Component.empty());
            meta.setHideTooltip(true);
        });

        // Handle filler
        GuiItem item = new GuiItem(fillerItem);
        GuiFiller filler = gui.getFiller();

        switch (fillerType) {
            case ALL -> {
                if (gui instanceof PaginatedGui) {
                    Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Paginated GUIs cannot use FillerType.ALL");
                    return;
                }
                filler.fill(item);
            }
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

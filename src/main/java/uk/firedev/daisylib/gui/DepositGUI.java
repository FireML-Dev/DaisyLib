package uk.firedev.daisylib.gui;

import de.themoep.inventorygui.GuiStorageElement;
import de.themoep.inventorygui.InventoryGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class DepositGUI {

    private final InventoryGui gui;
    private final Inventory inv;
    private final Player player;

    public DepositGUI(@NotNull Player player, @NotNull Plugin plugin, @NotNull Consumer<List<ItemStack>> consumer, @NotNull String title) {
        this.player = player;
        String[] setup = {
                "ddddddddd",
                "ddddddddd",
                "ddddddddd",
                "ddddddddd",
                "ddddddddd",
                "ddddddddd"
        };
        gui = new InventoryGui(plugin, title, setup);
        inv = Bukkit.createInventory(null, 54);

        gui.addElement(new GuiStorageElement('d', inv));

        gui.setCloseAction(close -> {
            List<ItemStack> contents = Arrays.stream(inv.getContents())
                    .filter(Objects::nonNull)
                    .toList();
            if (contents.isEmpty() || close.getPlayer() != player) {
                return true;
            }
            consumer.accept(contents);
            inv.clear();
            return true;
        });
    }

    public void open() {
        gui.show(player);
    }

}

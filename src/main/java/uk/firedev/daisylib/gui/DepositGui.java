package uk.firedev.daisylib.gui;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.StorageGui;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class DepositGui {

    private final Player player;
    private final StorageGui gui;

    public DepositGui(@NonNull Player player, @NonNull Component title, @NonNull Consumer<List<ItemStack>> consumer) {
        this.player = player;

        gui = Gui.storage()
            .title(title)
            .rows(6)
            .create();

        gui.setCloseGuiAction(event -> {
            List<ItemStack> contents = Arrays.stream(event.getInventory().getStorageContents())
                .filter(Objects::nonNull)
                .toList();
            if (contents.isEmpty() || event.getPlayer() != player) {
                return;
            }
            consumer.accept(contents);
            event.getInventory().clear();
        });
    }

    public void open() {
        gui.open(player);
    }

}

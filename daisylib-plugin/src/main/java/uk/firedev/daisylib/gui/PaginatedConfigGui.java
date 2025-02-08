package uk.firedev.daisylib.gui;

import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.message.component.ComponentMessage;

public class PaginatedConfigGui extends ConfigGui {

    public PaginatedConfigGui(@NotNull ConfigurationSection config, @NotNull Player player) {
        super(config, player);
        super.actions.put("next-page", event -> {
            if (getGui() instanceof PaginatedGui pages) {
                pages.next();
            }
            event.setCancelled(true);
        });
        super.actions.put("previous-page", event -> {
            if (getGui() instanceof PaginatedGui pages) {
                pages.previous();
            }
            event.setCancelled(true);
        });
    }

    @Override
    protected BaseGui createGui() {
        ConfigurationSection config = getGuiConfig();

        ComponentMessage title = ComponentMessage.fromConfig(config, "title", "Gui");

        PaginatedGui gui = Gui.paginated()
            .disableAllInteractions()
            .title(title.getMessage())
            .rows(config.getInt("rows", 6))
            .pageSize(config.getInt("page-size", 45))
            .create();

        // Load configured items
        loadItems(gui);

        // Load filler
        loadFiller(gui);

        return gui;
    }

}

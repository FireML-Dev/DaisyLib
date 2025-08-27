package uk.firedev.daisylib.gui;

import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.messagelib.config.PaperConfigLoader;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;

public class PaginatedConfigGui extends ConfigGui {

    public PaginatedConfigGui(@Nullable ConfigurationSection config, @NotNull Player player) {
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

        PaperConfigLoader loader = new PaperConfigLoader(config);
        ComponentMessage title = ComponentMessage.componentMessage(loader, "title");
        if (title == null) {
            title = ComponentMessage.componentMessage(Component.text("Gui"));
        }

        ComponentSingleMessage singleMessage = ComponentSingleMessage.componentSingleMessage(title);

        PaginatedGui gui = Gui.paginated()
            .disableAllInteractions()
            .title(singleMessage.get())
            .rows(config.getInt("rows", 6))
            .pageSize(config.getInt("page-size", 45))
            .create();

        // Load filler
        loadFiller(gui);

        // Load configured items
        loadItems(gui);

        return gui;
    }

}

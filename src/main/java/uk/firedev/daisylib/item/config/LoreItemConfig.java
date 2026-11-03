package uk.firedev.daisylib.item.config;

import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.messages.message.ComponentMessage;
import uk.firedev.daisylib.messages.replacer.Replacer;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class LoreItemConfig extends ItemConfig<List<Component>> {

    public LoreItemConfig(@NonNull ConfigurationSection section) {
        super(section);
    }

    public LoreItemConfig(@NonNull LoreItemConfig base) {
        super(base);
    }

    @Override
    public List<Component> getConfiguredValue() {
        List<String> lore = section.getStringList("lore");
        return lore.isEmpty() ? null : ComponentMessage.componentMessage(lore).get();
    }

    @Override
    protected BiConsumer<ItemStack, List<Component>> applyToItem(@Nullable OfflinePlayer player, @Nullable Replacer replacer) {
        return (item, value) -> {
            if (value.isEmpty()) {
                return;
            }
            String name = Optional.ofNullable(player)
                .map(OfflinePlayer::getName)
                .orElse("N/A");
            Replacer finalReplacer = Replacer.component().addReplacement("{player}", name);
            if (replacer != null) {
                finalReplacer.merge(replacer);
            }
            item.editMeta(meta -> {
                List<Component> lore = ComponentMessage.componentMessage(value)
                    .replace(finalReplacer)
                    .parsePlaceholderAPI(player)
                    .get();
                meta.lore(lore);
            });
        };
    }

    @Override
    public @NonNull LoreItemConfig createCopy() {
        return new LoreItemConfig(this);
    }

}

package uk.firedev.daisylib.api.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class PAPIWrapper extends PlaceholderExpansion {

    private final LegacyComponentSerializer legacyComponentSerializer = LegacyComponentSerializer.legacySection();
    private final Plugin plugin;
    private final Map<String, Supplier<Component>> globalMap;
    private final Map<String, Function<Audience, Component>> audienceMap;

    protected PAPIWrapper(@NotNull Plugin plugin, @NotNull Map<String, Supplier<Component>> globalMap, @NotNull Map<String, Function<Audience, Component>> audienceMap) {
        this.plugin = plugin;
        this.globalMap = globalMap;
        this.audienceMap = audienceMap;
    }

    @NotNull
    @Override
    public String getIdentifier() {
        return plugin.getPluginMeta().getName();
    }

    @NotNull
    @Override
    public String getAuthor() {
        return String.join(", ", plugin.getPluginMeta().getAuthors());
    }

    @NotNull
    @Override
    public String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() { return true; }

    @Override
    public boolean canRegister() { return true; }

    @Override
    public String onPlaceholderRequest(final Player player, @NotNull final String identifier) {
        Supplier<Component> globalSupplier = globalMap.get(identifier);
        if (globalSupplier != null) {
            return legacyComponentSerializer.serialize(globalSupplier.get());
        }
        if (player == null) {
            return null;
        }
        Function<Audience, Component> playerFunction = audienceMap.get(identifier);
        if (playerFunction != null) {
            return legacyComponentSerializer.serialize(playerFunction.apply(player));
        }
        return null;
    }


}

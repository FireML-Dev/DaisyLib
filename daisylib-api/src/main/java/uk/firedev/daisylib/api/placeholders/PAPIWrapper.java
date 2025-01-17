package uk.firedev.daisylib.api.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class PAPIWrapper extends PlaceholderExpansion {

    private final LegacyComponentSerializer legacyComponentSerializer = LegacyComponentSerializer.legacySection();
    private final Plugin plugin;
    private final Map<String, Supplier<Component>> globalMap;
    private final Map<String, Function<String, Component>> globalDynamicMap;
    private final Map<String, Function<Audience, Component>> audienceMap;
    Map<String, BiFunction<Audience, String, Component>> audienceDynamicMap;

    protected PAPIWrapper(@NotNull Plugin plugin, @NotNull Map<String, Supplier<Component>> globalMap, @NotNull Map<String, Function<String, Component>> globalDynamicMap, @NotNull Map<String, Function<Audience, Component>> audienceMap, Map<String, BiFunction<Audience, String, Component>> audienceDynamicMap) {
        this.plugin = plugin;
        this.globalMap = globalMap;
        this.globalDynamicMap = globalDynamicMap;
        this.audienceMap = audienceMap;
        this.audienceDynamicMap = audienceDynamicMap;
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
        if (player != null) {
            Function<Audience, Component> playerFunction = audienceMap.get(identifier);
            if (playerFunction != null) {
                return legacyComponentSerializer.serialize(playerFunction.apply(player));
            }
        }

        // Get the parts of a dynamic placeholder
        int lastUnderscore = identifier.lastIndexOf("_");
        if (lastUnderscore == -1) {
            return null;
        }
        System.out.println(identifier);
        String dynamicIdentifier = identifier.substring(0, lastUnderscore);
        String dynamicValue = identifier.substring(lastUnderscore + 1);

        // Check dynamic parts
        Function<String, Component> globalDynamicFunction = globalDynamicMap.get(dynamicIdentifier);
        if (globalDynamicFunction != null) {
            return legacyComponentSerializer.serialize(globalDynamicFunction.apply(dynamicValue));
        }
        if (player != null) {
            BiFunction<Audience, String, Component> playerFunction = audienceDynamicMap.get(dynamicIdentifier);
            if (playerFunction != null) {
                return legacyComponentSerializer.serialize(playerFunction.apply(player, dynamicValue));
            }
        }

        return null;
    }


}

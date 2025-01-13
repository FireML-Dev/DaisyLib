package uk.firedev.daisylib.api.placeholders;

import io.github.miniplaceholders.api.Expansion;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Allows easy creation of placeholders for both PlaceholderAPI and MiniPlaceholders.
 */
public class PlaceholderProvider {

    private final Plugin plugin;
    private final Map<String, Supplier<Component>> globalPlaceholderMap = new HashMap<>();
    private final Map<String, Function<Audience, Component>> audiencePlaceholderMap = new HashMap<>();

    private PlaceholderProvider(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    public static PlaceholderProvider create(@NotNull Plugin plugin) {
        return new PlaceholderProvider(plugin);
    }

    public PlaceholderProvider addGlobalPlaceholder(@NotNull String placeholder, @NotNull Supplier<Component> supplier) {
        this.globalPlaceholderMap.putIfAbsent(placeholder.toLowerCase(), supplier);
        return this;
    }

    public PlaceholderProvider addAudiencePlaceholder(@NotNull String placeholder, @NotNull Function<Audience, Component> function) {
        this.audiencePlaceholderMap.putIfAbsent(placeholder.toLowerCase(), function);
        return this;
    }

    public void register() {
        registerPlaceholderAPI();
        registerMiniPlaceholders();
    }

    public void registerPlaceholderAPI() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return;
        }
        new PAPIWrapper(plugin, globalPlaceholderMap, audiencePlaceholderMap).register();
    }

    public void registerMiniPlaceholders() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("MiniPlaceholders")) {
            return;
        }

        // Create this expansion
        Expansion.Builder builder = Expansion.builder(plugin.getPluginMeta().getName());

        // Add global placeholders
        for (Map.Entry<String, Supplier<Component>> entry : globalPlaceholderMap.entrySet()) {
            String placeholder = entry.getKey();
            Supplier<Component> value = entry.getValue();
            builder = builder.globalPlaceholder(placeholder, Tag.selfClosingInserting(value.get()));
        }

        // Add player placeholders
        for (Map.Entry<String, Function<Audience, Component>> entry : audiencePlaceholderMap.entrySet()) {
            String placeholder = entry.getKey();
            Function<Audience, Component> value = entry.getValue();
            builder = builder.audiencePlaceholder(placeholder, (audience, argumentQueue, context) ->
                    Tag.selfClosingInserting(value.apply(audience)));
        }
        // Register this expansion
        builder.build().register();
    }

}

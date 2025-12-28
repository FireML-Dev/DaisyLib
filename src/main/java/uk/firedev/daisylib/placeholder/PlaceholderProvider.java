package uk.firedev.daisylib.placeholder;

import io.github.miniplaceholders.api.Expansion;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class PlaceholderProvider {

    private final Plugin plugin;
    private final Map<String, Supplier<Component>> globalMap = new HashMap<>();
    private final Map<String, Function<String, Component>> globalDynamicMap = new HashMap<>();
    private final Map<String, Function<Audience, Component>> audienceMap = new HashMap<>();
    private final Map<String, BiFunction<Audience, String, Component>> audienceDynamicMap = new HashMap<>();

    public PlaceholderProvider(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Adds a new global placeholder.
     * @param placeholder The placeholder itself.
     * @param supplier A supplier for the placeholder's replacement
     */
    public PlaceholderProvider addGlobalPlaceholder(@NotNull String placeholder, @NotNull Supplier<Component> supplier) {
        this.globalMap.putIfAbsent(placeholder.toLowerCase(), supplier);
        return this;
    }

    /**
     * Adds a new dynamic global placeholder.
     * @param placeholder The placeholder itself, before the dynamic part.
     * @param function A function that uses the dynamic value to get the placeholder's replacement.
     */
    public PlaceholderProvider addGlobalDynamicPlaceholder(@NotNull String placeholder, @NotNull Function<String, Component> function) {
        this.globalDynamicMap.putIfAbsent(placeholder, function);
        return this;
    }

    /**
     * Adds a new audience placeholder.
     * @param placeholder The placeholder itself.
     * @param function A function that uses the found audience to get the placeholder's replacement.
     */
    public PlaceholderProvider addAudiencePlaceholder(@NotNull String placeholder, @NotNull Function<Audience, Component> function) {
        this.audienceMap.putIfAbsent(placeholder.toLowerCase(), function);
        return this;
    }

    /**
     * Adds a new dynamic audience placeholder.
     * @param placeholder The placeholder itself, before the dynamic part.
     * @param function A function that uses the found audience and the dynamic value to get the placeholder's replacement.
     */
    public PlaceholderProvider addAudienceDynamicPlaceholder(@NotNull String placeholder, @NotNull BiFunction<Audience, String, Component> function) {
        this.audienceDynamicMap.putIfAbsent(placeholder.toLowerCase(), function);
        return this;
    }

    /**
     * Registers this provider with PlaceholderAPI and MiniPlaceholders
     */
    public void register() {
        registerPlaceholderAPI();
        registerMiniPlaceholders();
    }

    /**
     * Registers this provider with PlaceholderAPI
     */
    public void registerPlaceholderAPI() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return;
        }
        //new PAPIWrapper(plugin, globalMap, globalDynamicMap, audienceMap, audienceDynamicMap).register();
    }

    /**
     * Registers this provider with MiniPlaceholders
     */
    public void registerMiniPlaceholders() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("MiniPlaceholders")) {
            return;
        }

        // Create this expansion
        Expansion.Builder builder = Expansion.builder(plugin.getPluginMeta().getName());

        // Add global placeholders
        for (Map.Entry<String, Supplier<Component>> entry : globalMap.entrySet()) {
            String placeholder = entry.getKey();
            Supplier<Component> value = entry.getValue();
            builder = builder.globalPlaceholder(placeholder, Tag.selfClosingInserting(value.get()));
        }

        // Add player placeholders
        for (Map.Entry<String, Function<Audience, Component>> entry : audienceMap.entrySet()) {
            String placeholder = entry.getKey();
            Function<Audience, Component> value = entry.getValue();
            builder = builder.audiencePlaceholder(placeholder, (audience, argumentQueue, context) ->
                Tag.selfClosingInserting(value.apply(audience)));
        }

        // Add dynamic global placeholders
        for (Map.Entry<String, Function<String, Component>> entry : globalDynamicMap.entrySet()) {
            String placeholder = entry.getKey();
            Function<String, Component> function = entry.getValue();
            builder = builder.globalPlaceholder(placeholder, (queue, ctx) -> {
                String value = queue.popOr("Could not find value").value();
                return Tag.selfClosingInserting(function.apply(value));
            });
        }

        // Add dynamic audience placeholders
        for (Map.Entry<String, BiFunction<Audience, String, Component>> entry : audienceDynamicMap.entrySet()) {
            String placeholder = entry.getKey();
            BiFunction<Audience, String, Component> function = entry.getValue();
            builder = builder.audiencePlaceholder(placeholder, (audience, queue, ctx) -> {
                String value = queue.popOr("Could not find value").value();
                return Tag.selfClosingInserting(function.apply(audience, value));
            });
        }

        // Register this expansion
        builder.build().register();
    }

}

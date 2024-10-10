package uk.firedev.daisylib.message.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.message.Replacer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentReplacer implements Replacer {

    private String prefix = "{";
    private String suffix = "}";
    private Map<String, Component> replacements = new HashMap<>();

    /**
     * @deprecated This constructor will be made private for 2.1.0-SNAPSHOT. Use {@link #componentReplacer()} instead.
     */
    @Deprecated(forRemoval = true)
    public ComponentReplacer() {}

    public static ComponentReplacer componentReplacer() { return new ComponentReplacer(); }

    public static ComponentReplacer componentReplacer(@NotNull Map<String, Component> replacements) {
        return new ComponentReplacer().addReplacements(replacements);
    }

    public static ComponentReplacer componentReplacer(@NotNull String... replacements) {
        return new ComponentReplacer().addReplacements(replacements);
    }

    public static ComponentReplacer componentReplacer(@NotNull String string, @NotNull Component component) {
        return new ComponentReplacer().addReplacement(string, component);
    }

    public static ComponentReplacer componentReplacer(@NotNull String string, @NotNull String replacement) {
        return new ComponentReplacer().addReplacements(string, replacement);
    }

    /**
     * Adds a map of replacements.
     * @param replacements The replacements to add.
     * @return The modified ComponentReplacer
     */
    public ComponentReplacer addReplacements(@NotNull Map<String, Component> replacements) {
        getModifiableReplacements().putAll(replacements);
        return this;
    }

    /**
     * Adds a map of replacements, built from an array.
     * @param replacements The replacements to add.
     * @return The modified ComponentReplacer
     */
    public ComponentReplacer addReplacements(@NotNull String... replacements) {
        if (!(replacements.length % 2 == 0)) {
            throw new IllegalArgumentException("The replacement length has to be an even number!");
        }
        Map<String, Component> map = new HashMap<>();
        for (int i = 0; i + 1 < replacements.length; i += 2) {
            map.put(replacements[i], ComponentMessage.fromString(replacements[i + 1]).getMessage());
        }
        getModifiableReplacements().putAll(map);
        return this;
    }

    /**
     * Adds a map of replacements, built from the provided String and Component.
     * @param string The string to replace
     * @param component The component to replace the string with
     * @return The modified ComponentReplacer
     */
    public ComponentReplacer addReplacement(@NotNull String string, @NotNull Component component) {
        getModifiableReplacements().put(string, component);
        return this;
    }

    /**
     * Adds a map of replacements, built from the provided String and replacement String.
     * @param string The string to replace
     * @param replacement The replacement string
     * @return The modified ComponentReplacer
     */
    public ComponentReplacer addReplacement(@NotNull String string, @NotNull String replacement) {
        getModifiableReplacements().put(string, ComponentMessage.fromString(replacement).getMessage());
        return this;
    }

    /**
     * Merges multiple ComponentReplacers into this one.
     * The merged replacements will use this instance's prefix/suffix values.
     * Existing replacements will be skipped.
     * @param replacers The replacers to merge
     * @return The modified ComponentReplacer
     */
    public ComponentReplacer mergeReplacers(@NotNull ComponentReplacer... replacers) {
        for (final ComponentReplacer replacer : replacers) {
            replacer.getReplacements().forEach((key, value) -> getModifiableReplacements().putIfAbsent(key, value));
        }
        return this;
    }

    /**
     * Clears the replacement map.
     * @return The modified ComponentReplacer
     */
    public ComponentReplacer clearReplacements() {
        getModifiableReplacements().clear();
        return this;
    }

    private Map<String, Component> getModifiableReplacements() {
        if (this.replacements == null) {
            this.replacements = new HashMap<>();
        }
        return this.replacements;
    }

    /**
     * Get the replacement map. This cannot be modified.
     * @return The map of replacements.
     */
    public Map<String, Component> getReplacements() {
        if (this.replacements == null) {
            this.replacements = new HashMap<>();
        }
        return Map.copyOf(this.replacements);
    }

    /**
     * Sets the placeholder prefix.
     * @param prefix The prefix to use
     * @return The modified ComponentReplacer.
     */
    @Override
    public ComponentReplacer prefix(@NotNull String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Gets the placeholder prefix
     * @return The placeholder prefix.
     */
    @Override
    public String prefix() { return this.prefix; }

    /**
     * Sets the placeholder suffix.
     * @param suffix The suffix to use
     * @return The modified ComponentReplacer.
     */
    @Override
    public ComponentReplacer suffix(@NotNull String suffix) {
        this.suffix = suffix;
        return this;
    }

    /**
     * Gets the placeholder suffix
     * @return The placeholder suffix.
     */
    @Override
    public String suffix() { return suffix; }

    /**
     * Applies all replacements to the provided Component.
     * @param component The component to apply replacements to.
     * @return The replaced component.
     */
    public Component replace(@NotNull Component component) {
        Map<String, Component> replacements = getReplacements();
        TextReplacementConfig.Builder trc = TextReplacementConfig.builder();
        for (Map.Entry<String, Component> entry : replacements.entrySet()) {
            Component replaceComponent = entry.getValue();
            String placeholder = prefix() + entry.getKey() + suffix();
            trc.matchLiteral(placeholder).replacement(replaceComponent);
            component = component.replaceText(trc.build());
        }
        return component;
    }

    /**
     * Applies all replacements to the provided Components.
     * @param components The components to apply replacements to.
     * @return The replaced components.
     */
    public List<Component> replace(@NotNull List<Component> components) {
        return components.stream().map(this::replace).toList();
    }

}
package uk.firedev.daisylib.message.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentReplacer {

    private String prefix = "{";
    private String suffix = "}";
    private Map<String, Component> replacements = new HashMap<>();

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
            map.put(replacements[i], ComponentUtils.deserializeString(replacements[i + 1]));
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
    public ComponentReplacer prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Gets the placeholder prefix
     * @return The placeholder prefix.
     */
    public String prefix() { return this.prefix; }

    /**
     * Sets the placeholder suffix.
     * @param suffix The suffix to use
     * @return The modified ComponentReplacer.
     */
    public ComponentReplacer suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    /**
     * Gets the placeholder suffix
     * @return The placeholder suffix.
     */
    public String suffix() { return suffix; }

    /**
     * Applies all replacements to the provided Component.
     * @param component The component to apply replacements to.
     * @return The replaced component.
     */
    public Component replace(@NotNull Component component) {
        Map<String, Component> replacements = getReplacements();
        for (String string : replacements.keySet()) {
            Component replaceComponent = replacements.get(string);
            String placeholder = prefix() + string + suffix();
            TextReplacementConfig config = TextReplacementConfig.builder().matchLiteral(placeholder).replacement(replaceComponent).build();
            component = component.replaceText(config);
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
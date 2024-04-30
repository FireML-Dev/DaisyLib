package uk.firedev.daisylib.message.string;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class StringReplacer {

    private String prefix = "{";
    private String suffix = "}";
    private Map<String, String> replacements = new HashMap<>();

    /**
     * Adds a map of replacements.
     * @param replacements The replacements to add.
     * @return The modified StringReplacer
     */
    public StringReplacer addReplacements(@NotNull Map<String, String> replacements) {
        getModifiableReplacements().putAll(replacements);
        return this;
    }

    /**
     * Adds a map of replacements, built from an array.
     * @param replacements The replacements to add.
     * @return The modified StringReplacer
     */
    public StringReplacer addReplacements(@NotNull String... replacements) {
        if (!(replacements.length % 2 == 0)) {
            throw new IllegalArgumentException("The replacement length has to be an even number!");
        }
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i + 1 < replacements.length; i += 2) {
            map.put(replacements[i], replacements[i + 1]);
        }
        getModifiableReplacements().putAll(map);
        return this;
    }

    /**
     * Adds a map of replacements, built from the provided String and replacement String.
     * @param string The string to replace
     * @param replacement The replacement string.
     * @return The modified StringReplacer
     */
    public StringReplacer addReplacement(@NotNull String string, @NotNull String replacement) {
        getModifiableReplacements().put(string, replacement);
        return this;
    }

    /**
     * Clears the replacement map.
     * @return The modified StringReplacer
     */
    public StringReplacer clearReplacements() {
        getModifiableReplacements().clear();
        return this;
    }

    private Map<String, String> getModifiableReplacements() {
        if (this.replacements == null) {
            this.replacements = new HashMap<>();
        }
        return this.replacements;
    }

    /**
     * Get the replacement map. This cannot be modified.
     * @return The map of replacements.
     */
    public Map<String, String> getReplacements() {
        if (this.replacements == null) {
            this.replacements = new HashMap<>();
        }
        return Map.copyOf(this.replacements);
    }

    /**
     * Sets the placeholder prefix.
     * @param prefix The prefix to use
     * @return The modified StringReplacer.
     */
    public StringReplacer prefix(String prefix) {
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
     * @return The modified StringReplacer.
     */
    public StringReplacer suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    /**
     * Gets the placeholder suffix
     * @return The placeholder suffix.
     */
    public String suffix() { return suffix; }

    /**
     * Applies all replacements to the provided String.
     * @param string The string to apply replacements to.
     * @return The replaced string.
     */
    public String replace(@NotNull String string) {
        Map<String, String> map = getReplacements();
        for (String key : map.keySet()) {
            String replacement = map.get(key);
            String placeholder = prefix() + key + suffix();
            string = string.replace(placeholder, replacement);
        }
        return string;
    }

}
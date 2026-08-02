package uk.firedev.daisylib.messages.replacer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.messages.ObjectProcessor;
import uk.firedev.daisylib.messages.message.ComponentMessage;
import uk.firedev.daisylib.messages.message.ComponentSingleMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Replacer {

    private final Map<String, Object> replacements = new HashMap<>();

    private Replacer() {}

    /**
     * Creates a new Replacer instance.
     * @return The new Replacer instance.
     */
    public static Replacer replacer() {
        return new Replacer();
    }

    /**
     * Adds a single replacement to this Replacer.
     * @param string The string to replace.
     * @param replacement The replacement object. Explicitly supports {@link Component} and {@link ComponentSingleMessage}. Anything else will be converted to a String and processed.
     * @return The modified Replacer.
     */
    public Replacer addReplacement(@NonNull String string, @Nullable Object replacement) {
        this.replacements.put(string, replacement);
        return this;
    }

    /**
     * Adds a map of replacements to this Replacer.
     * @param replacements The replacements to add. Explicitly supports {@link Component} and {@link ComponentSingleMessage} as values. Anything else will be converted to a String and processed.
     * @return The modified Replacer
     */
    public Replacer addReplacements(@NonNull Map<String, ?> replacements) {
        this.replacements.putAll(replacements);
        return this;
    }

    /**
     * Merges other Replacers into this one.
     * @param others The other Replacers to merge.
     * @return The modified Replacer.
     */
    public Replacer merge(@NonNull Replacer... others) {
        for (Replacer other : others) {
            if (other == this) {
                continue;
            }
            replacements.putAll(other.replacements);
        }
        return this;
    }

    /**
     * Clears all replacements from this Replacer.
     * @return The modified Replacer.
     */
    public Replacer clear() {
        this.replacements.clear();
        return this;
    }

    /**
     * Applies the replacements to the provided Component.
     * @param component The component to apply the replacements to.
     * @return The modified component.
     */
    public Component apply(@NonNull Component component) {
        for (Map.Entry<String, Object> entry : replacements.entrySet()) {
            Component replacement = Component.join(JoinConfiguration.newlines(), ObjectProcessor.process(entry.getValue()));
            component = component.replaceText(
                builder -> builder.matchLiteral(entry.getKey()).replacement(replacement)
            );
        }
        return component;
    }

    /**
     * Applies the replacements to a list of Components.
     * @param components The list of components to apply the replacements to.
     * @return The modified list of components.
     */
    public List<Component> apply(@NonNull List<Component> components) {
        return components.stream().map(this::apply).toList();
    }

    /**
     * Applies the replacements to a list of Components.
     * <p>
     * If a variable is found, the replacement list is inserted in place of the variable's entry.
     * @param components The list of components to apply the replacements to.
     * @return The modified list of components.
     */
    // TODO potentially clean up at some point?
    public List<Component> applyWithListInsertion(@NonNull List<Component> components) {
        List<Component> newList = new ArrayList<>();
        for (Component component : components) {
            boolean replaced = false;
            for (Map.Entry<String, Object> entry : replacements.entrySet()) {
                ComponentSingleMessage single = ComponentMessage.componentMessage(component);
                if (single.contains(entry.getKey())) {
                    newList.addAll(ObjectProcessor.process(entry.getValue()));
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                newList.add(component);
            }
        }
        return newList;
    }

}

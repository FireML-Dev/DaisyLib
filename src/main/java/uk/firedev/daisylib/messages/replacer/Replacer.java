package uk.firedev.daisylib.messages.replacer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.messages.ObjectProcessor;
import uk.firedev.daisylib.messages.message.ComponentMessage;
import uk.firedev.daisylib.messages.message.ComponentSingleMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replacer {

    private static final Pattern PATTERN = Pattern.compile("\\{([^{}]*)}");

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
        if (replacements.isEmpty()) {
            return component;
        }
        return apply(component, getResolver());
    }

    /**
     * Applies the replacements to a list of Components.
     * @param components The list of components to apply the replacements to.
     * @return The modified list of components.
     */
    public List<Component> apply(@NonNull List<Component> components) {
        if (replacements.isEmpty()) {
            return components;
        }
        TagResolver resolver = getResolver();
        return components.stream()
            .map(component -> apply(component, resolver))
            .toList();
    }

    private Component apply(Component component, TagResolver resolver) {
        MiniMessage mm = MiniMessage.miniMessage();
        String string = mm.serialize(component);
        return mm.deserialize(processVariables(string), resolver);
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

    private TagResolver getResolver() {
        TagResolver.Builder builder = TagResolver.builder();
        replacements.forEach((variable, replacement) -> {
            Component parsed = Component.join(JoinConfiguration.commas(true), ObjectProcessor.process(replacement));
            builder.resolver(TagResolver.resolver(
                // Safe to ignore "unsubstituted expression" here,
                getVariableName(variable),
                Tag.selfClosingInserting(parsed)
            ));
        });
        return builder.build();
    }

    private String processVariables(String string) {
        Matcher matcher = PATTERN.matcher(string);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String exactMatch = matcher.group();
            if (replacements.containsKey(exactMatch)) {
                String name = exactMatch.substring(1, exactMatch.length() - 1);
                String replacement = "<" + name + ">";
                matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
            } else {
                matcher.appendReplacement(result, Matcher.quoteReplacement(exactMatch));
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private String getVariableName(String string) {
        return PATTERN.matcher(string).replaceAll("$1");
    }

}

package uk.firedev.daisylib.messages.replacer;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public abstract class Replacer {

    protected final Map<String, @Nullable Object> replacements = new HashMap<>();

    /**
     * Creates a new {@link ComponentReplacer} instance.
     */
    public static Replacer component() {
        return new ComponentReplacer();
    }

    /**
     * Creates a new {@link PlainTextReplacer} instance.
     */
    public static Replacer plainText() {
        return new PlainTextReplacer();
    }

    public Replacer addReplacement(String string, @Nullable Object replacement) {
        this.replacements.put(string, replacement);
        return this;
    }

    public Replacer addReplacements(@Nullable Map<String, ?> replacements) {
        if (replacements != null) {
            this.replacements.putAll(replacements);
        }
        return this;
    }

    public Replacer merge(Replacer... others) {
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
    public abstract Component apply(Component component);

    /**
     * Applies the replacements to a list of Components.
     * @param components The list of components to apply the replacements to.
     * @return The modified list of components.
     */
    public abstract List<Component> apply(List<Component> components);

    /**
     * Applies the replacements to a list of Components.
     * <p>
     * If a variable is found, the replacement list is inserted in place of the variable's entry.
     * @param components The list of components to apply the replacements to.
     * @return The modified list of components.
     */
    public abstract List<Component> applyWithListInsertion(List<Component> components);

}

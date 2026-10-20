package uk.firedev.daisylib.messages.replacer;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.messages.message.ComponentSingleMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Replacer {

    protected final Map<String, Object> replacements = new HashMap<>();

    /**
     * @deprecated Use {@link #component()} instead.
     */
    @Deprecated(forRemoval = true)
    public static Replacer replacer() {
        return new ComponentReplacer();
    }

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

    public Replacer addReplacement(@NonNull String string, @Nullable Object replacement) {
        this.replacements.put(string, replacement);
        return this;
    }

    public Replacer addReplacements(@NonNull Map<String, ?> replacements) {
        this.replacements.putAll(replacements);
        return this;
    }

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
    public abstract Component apply(@NonNull Component component);

    /**
     * Applies the replacements to a list of Components.
     * @param components The list of components to apply the replacements to.
     * @return The modified list of components.
     */
    public abstract List<Component> apply(@NonNull List<Component> components);

    /**
     * Applies the replacements to a list of Components.
     * <p>
     * If a variable is found, the replacement list is inserted in place of the variable's entry.
     * @param components The list of components to apply the replacements to.
     * @return The modified list of components.
     */
    public abstract List<Component> applyWithListInsertion(@NonNull List<Component> components);

}

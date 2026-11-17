package uk.firedev.daisylib.messages.replacer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.messages.MessageSettings;
import uk.firedev.daisylib.messages.ObjectProcessor;
import uk.firedev.daisylib.messages.message.ComponentMessage;
import uk.firedev.daisylib.messages.message.ComponentSingleMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlainTextReplacer extends Replacer {

    /**
     * Applies the replacements to the provided Component.
     *
     * @param component The component to apply the replacements to.
     * @return The modified component.
     */
    @Override
    public Component apply(@NonNull Component component) {
        if (replacements.isEmpty()) {
            return component;
        }
        MiniMessage mm = MessageSettings.getMiniMessage();
        String string = mm.serialize(component);
        for (Map.Entry<String, Object> entry : replacements.entrySet()) {
            string = string.replace(entry.getKey(), entry.getValue().toString());
        }
        return mm.deserialize(string);
    }

    /**
     * Applies the replacements to a list of Components.
     *
     * @param components The list of components to apply the replacements to.
     * @return The modified list of components.
     */
    @Override
    public List<Component> apply(@NonNull List<Component> components) {
        if (replacements.isEmpty()) {
            return components;
        }
        return components.stream()
            .map(this::apply)
            .toList();
    }

    /**
     * Applies the replacements to a list of Components.
     * <p>
     * If a variable is found, the replacement list is inserted in place of the variable's entry.
     *
     * @param components The list of components to apply the replacements to.
     * @return The modified list of components.
     */
    @Override
    public List<Component> applyWithListInsertion(@NonNull List<Component> components) {
        MiniMessage mm = MessageSettings.getMiniMessage();
        List<Component> newList = new ArrayList<>();
        for (Component component : components) {
            boolean replaced = false;
            for (Map.Entry<String, Object> entry : replacements.entrySet()) {
                String serialized = mm.serialize(component);
                if (serialized.contains(entry.getKey())) {
                    newList.add(Component.text(entry.getValue().toString()));
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

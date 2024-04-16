package uk.firedev.daisylib.replacers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextDecoration;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.utils.ComponentUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentReplacer {

    private Component component;
    private String prefix = "{";
    private String suffix = "}";

    public ComponentReplacer(Component component) {
        this.component = component;
    }

    public ComponentReplacer duplicate() {
        return new ComponentReplacer(component).prefix(prefix).suffix(suffix);
    }

    public ComponentReplacer replace(Map<String, Component> replacements) {
        if (this.component == null || replacements == null || replacements.isEmpty()) {
            return this;
        }
        replacements.forEach((string, replaceComponent) -> {
            string = prefix + string + suffix;
            TextReplacementConfig config = TextReplacementConfig.builder().matchLiteral(string).replacement(replaceComponent).build();
            setComponent(component.replaceText(config));
        });
        return this;
    }

    public ComponentReplacer replace(String... replacements) {
        if (this.component == null || replacements == null) {
            return this;
        }
        if (!(replacements.length % 2 == 0)) {
            throw new IllegalArgumentException("The replacement length has to be an even number!");
        }
        Map<String, Component> map = new HashMap<>();
        for (int i = 0; i + 1 < replacements.length; i += 2) {
            map.put(replacements[i], ComponentUtils.deserializeString(replacements[i + 1]));
        }
        return replace(map);
    }

    public ComponentReplacer replace(String string, Component component) {
        return replace(Map.of(string, component));
    }

     /*
     * Probably won't ever be used but we have it here if we need it
     * There might be better ways to do this but I haven't found them yet
     */
    public ComponentReplacer replace(List<String> stringList, List<Component> componentList) {
        if (stringList == null || componentList == null) {
            return this;
        }
        if (stringList.size() != componentList.size()) {
            throw new IllegalArgumentException("Both lists must be the same size!");
        }
        Map<String, Component> map = new HashMap<>();
        int i = 0;
        while (i != stringList.size()) {
            try {
                map.put(stringList.get(i), componentList.get(i));
                i++;
            } catch (IndexOutOfBoundsException ex) {
                Loggers.logException(ex, DaisyLib.getInstance().getLogger());
                break;
            }
        }
        return replace(map);
    }

    public ComponentReplacer setComponent(Component component) {
        this.component = component;
        return this;
    }

    public ComponentReplacer prefix(String character) {
        prefix = character;
        return this;
    }

    public String prefix() { return prefix; }

    public ComponentReplacer suffix(String character) {
        suffix = character;
        return this;
    }

    public String suffix() { return suffix; }

    public Component build() {
        return component.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public StringReplacer toStringReplacer() {
        return new StringReplacer(ComponentUtils.serializeComponent(component))
                .prefix(this.prefix)
                .suffix(this.suffix);
    }

}
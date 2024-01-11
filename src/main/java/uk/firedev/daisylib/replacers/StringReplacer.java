package uk.firedev.daisylib.replacers;

import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.utils.ComponentUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringReplacer {

    private String string;
    private String prefix = "{";
    private String suffix = "}";

    public StringReplacer(String string) {
        this.string = string;
    }

    public StringReplacer duplicate() {
        return new StringReplacer(string).prefix(prefix).suffix(suffix);
    }

    public StringReplacer replace(Map<String, String> replacements) {
        if (string == null || replacements == null) {
            return this;
        }
        replacements.forEach((string, replaceString) -> {
            string = prefix + string + suffix;
            if (!isStringEmpty()) {
                setString(this.string.replace(string, replaceString));
            }
        });
        return this;
    }

    public StringReplacer replace(String... replacements) {
        if (string == null || replacements == null) {
            return this;
        }
        if (!(replacements.length % 2 == 0)) {
            throw new IllegalArgumentException("The replacement length has to be an even number!");
        }
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i + 1 < replacements.length; i += 2) {
            map.put(replacements[i], replacements[i + 1]);
        }
        return replace(map);
    }

    public StringReplacer replace(String string, String replaceString) {
        return replace(Map.of(string, replaceString));
    }

    /*
    * Probably won't ever be used but we have it here if we need it
    * There might be better ways to do this but I haven't found them yet
    */
    public StringReplacer replace(List<String> stringList, List<String> replaceStringList) {
        if (stringList == null || replaceStringList == null) {
            return this;
        }
        if (stringList.size() != replaceStringList.size()) {
            throw new IllegalArgumentException("Both lists must be the same size!");
        }
        Map<String, String> map = new HashMap<>();
        int i = 0;
        while (i != stringList.size()) {
            try {
                map.put(stringList.get(i), replaceStringList.get(i));
                i++;
            } catch (IndexOutOfBoundsException ex) {
                Loggers.logException(ex, DaisyLib.getInstance().getLogger());
                break;
            }
        }
        return replace(map);
    }

    public StringReplacer setString(String string) {
        this.string = string;
        return this;
    }

    public StringReplacer prefix(String character) {
        prefix = character;
        return this;
    }

    public String prefix() {
        return prefix;
    }

    public StringReplacer suffix(String character) {
        suffix = character;
        return this;
    }

    public String suffix() {
        return suffix;
    }

    private boolean isStringEmpty() {
        return string.isEmpty();
    }

    public String build() {
        return string;
    }

    public ComponentReplacer toComponentReplacer() {
        return new ComponentReplacer(ComponentUtils.parseComponent(string))
                .prefix(this.prefix)
                .suffix(this.suffix);
    }

}

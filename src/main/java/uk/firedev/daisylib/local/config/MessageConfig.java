package uk.firedev.daisylib.local.config;

import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.Addon;
import uk.firedev.daisylib.config.ConfigBase;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.replacer.Replacer;

public class MessageConfig extends ConfigBase {

    private static final MessageConfig instance = new MessageConfig();

    private MessageConfig() {
        super("messages.yml", "messages.yml", DaisyLib.getInstance());
    }

    public static @NotNull MessageConfig getInstance() {
        return instance;
    }

    public Replacer getPrefixReplacer() {
        return Replacer.replacer().addReplacement("{prefix}", getPrefix());
    }

    // GENERAL MESSAGES

    public ComponentMessage getPrefix() {
        return getComponentMessage("prefix", "<gray>[DaisyLib]</gray> ");
    }

    // MAIN COMMAND MESSAGES

    public ComponentMessage getMainUsageMessage() {
        return getComponentMessage("main-command.usage", "{prefix}<#F0E68C>Usage: /daisylib reload")
            .replace(getPrefixReplacer());
    }

    public ComponentMessage getReloadedMessage() {
        return getComponentMessage("main-command.reloaded", "{prefix}<#F0E68C>Successfully reloaded the plugin.")
            .replace(getPrefixReplacer());
    }

    public ComponentMessage getNoAddonsMessage(@NotNull Class<? extends Addon> clazz) {
        return getComponentMessage("main-command.list-addons.none", "{prefix}<#F0E68C>There are no registered {name}s.")
            .replace("{name}", clazz.getSimpleName())
            .replace(getPrefixReplacer());
    }

    public ComponentMessage getListAddonsMessage(@NotNull Class<? extends Addon> clazz) {
        return getComponentMessage("main-command.list-addons.list", "{prefix}<#F0E68C>Registered {name}s: <green>{list}</green>")
            .replace("{name}", clazz.getSimpleName())
            .replace(getPrefixReplacer());
    }

}

package uk.firedev.daisylib.local.config;

import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.addons.Addon;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.message.component.ComponentReplacer;
import uk.firedev.daisylib.config.ConfigBase;
import uk.firedev.daisylib.local.DaisyLib;

public class MessageConfig extends ConfigBase {

    private static MessageConfig instance = null;

    private MessageConfig() {
        super("messages.yml", "messages.yml", DaisyLib.getInstance());
        withDefaultUpdaterSettings();
    }

    public static MessageConfig getInstance() {
        if (instance == null) {
            instance = new MessageConfig();
        }
        return instance;
    }

    public ComponentReplacer getPrefixReplacer() {
        return ComponentReplacer.create("prefix", getPrefix().getMessage());
    }

    // GENERAL MESSAGES

    public ComponentMessage getPrefix() {
        return getComponentMessage("prefix", "<gray>[DaisyLib]</gray> ");
    }

    // MAIN COMMAND MESSAGES

    public ComponentMessage getMainUsageMessage() {
        return getComponentMessage("main-command.usage", "{prefix}<#F0E68C>Usage: /daisylib reload")
            .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getReloadedMessage() {
        return getComponentMessage("main-command.reloaded", "{prefix}<#F0E68C>Successfully reloaded the plugin.")
            .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getNoAddonsMessage(@NotNull Class<? extends Addon> clazz) {
        return getComponentMessage("main-command.list-addons.none", "{prefix}<#F0E68C>There are no registered {name}s.")
            .replace("name", clazz.getSimpleName())
            .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getListAddonsMessage(@NotNull Class<? extends Addon> clazz) {
        return getComponentMessage("main-command.list-addons.list", "{prefix}<#F0E68C>Registered {name}s:</color> <green>{list}</green>")
            .replace("name", clazz.getSimpleName())
            .applyReplacer(getPrefixReplacer());
    }

}

package uk.firedev.daisylib.local.config;

import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;

public class MessageConfig extends uk.firedev.daisylib.Config {

    private static MessageConfig instance = null;

    private MessageConfig() {
        super("messages.yml", DaisyLib.getInstance(), true, true);
    }

    public static MessageConfig getInstance() {
        if (instance == null) {
            instance = new MessageConfig();
        }
        return instance;
    }

    public ComponentReplacer getPrefixReplacer() {
        return new ComponentReplacer().addReplacement("prefix", getPrefix().getMessage());
    }

    // GENERAL MESSAGES

    public ComponentMessage getPrefix() {
        return new ComponentMessage(getConfig(), "messages.prefix", "<gray>[DaisyLib]</gray> ");
    }

    // MAIN COMMAND MESSAGES

    public ComponentMessage getMainUsageMessage() {
        return new ComponentMessage(getConfig(), "messages.main-command.usage", "<aqua>Usage: /daisylib reload</aqua>")
                .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getReloadedMessage() {
        return new ComponentMessage(getConfig(), "messages.main-command.reloaded", "<aqua>Successfully reloaded the plugin.</aqua>")
                .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getNoRewardTypesMessage() {
        return new ComponentMessage(getConfig(), "messages.main-command.reward-types.none", "<aqua>There are no registered reward types.</aqua>")
                .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getListRewardTypesMessage() {
        return new ComponentMessage(getConfig(), "messages.main-command.reward-types.list", "<aqua>Registered Reward Types:</aqua> <green>{list}</green>")
                .applyReplacer(getPrefixReplacer());
    }

}

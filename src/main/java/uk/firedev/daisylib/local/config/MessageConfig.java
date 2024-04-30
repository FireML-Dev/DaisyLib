package uk.firedev.daisylib.local.config;

import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.message.component.ComponentMessage;

public class MessageConfig extends uk.firedev.daisylib.Config {

    private static MessageConfig instance = null;

    private MessageConfig() {
        super("messages.yml", DaisyLib.getInstance(), true);
    }

    public static MessageConfig getInstance() {
        if (instance == null) {
            instance = new MessageConfig();
        }
        return instance;
    }

    public ComponentMessage getPrefix() {
        return new ComponentMessage(getConfig(), "messages.prefix", "<gray>[DaisyLib]</gray> ");
    }

    public ComponentMessage getMainUsageMessage(boolean prefix) {
        ComponentMessage message = new ComponentMessage(
                getConfig(),
                "messages.main-command.usage",
                "<aqua>Usage: /daisylib reload</aqua>"
        );
        if (prefix) {
            message = message.addPrefix(getPrefix());
        }
        return message;
    }

    public ComponentMessage getReloadedMessage(boolean prefix) {
        ComponentMessage message = new ComponentMessage(
                getConfig(),
                "messages.main-command.reloaded",
                "<aqua>Successfully reloaded the plugin.</aqua>"
        );
        if (prefix) {
            message = message.addPrefix(getPrefix());
        }
        return message;
    }

    public ComponentMessage getNoRewardTypesMessage(boolean prefix) {
        ComponentMessage message = new ComponentMessage(
                getConfig(),
                "messages.main-command.reward-types.none",
                "<aqua>There are no registered reward types.</aqua>"
        );
        if (prefix) {
            message = message.addPrefix(getPrefix());
        }
        return message;
    }

    public ComponentMessage getListRewardTypesMessage(boolean prefix) {
        ComponentMessage message = new ComponentMessage(
                getConfig(),
                "messages.main-command.reward-types.list",
                "<aqua>Registered Reward Types:</aqua> <green>{list}</green>"
        );
        if (prefix) {
            message = message.addPrefix(getPrefix());
        }
        return message;
    }

}

package uk.firedev.daisylib.local.config;

import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.message.string.StringMessage;

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

    public StringMessage getPrefix() {
        return new StringMessage(getConfig(), "messages.prefix", "<gray>[DaisyLib]</gray> ");
    }

}

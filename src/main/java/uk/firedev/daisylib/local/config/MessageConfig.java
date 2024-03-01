package uk.firedev.daisylib.local.config;

import uk.firedev.daisylib.local.DaisyLib;

public class MessageConfig extends uk.firedev.daisylib.Config implements uk.firedev.daisylib.utils.MessageUtils {

    private static MessageConfig instance = null;

    private MessageConfig() {
        super("messages.yml", DaisyLib.getInstance());
    }

    public static MessageConfig getInstance() {
        if (instance == null) {
            instance = new MessageConfig();
        }
        return instance;
    }

}

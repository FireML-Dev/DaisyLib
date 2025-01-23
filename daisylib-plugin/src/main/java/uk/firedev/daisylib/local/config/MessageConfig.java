package uk.firedev.daisylib.local.config;

import uk.firedev.daisylib.config.ConfigBase;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.message.component.ComponentReplacer;

public class MessageConfig extends ConfigBase {

    private static MessageConfig instance = null;

    private MessageConfig() {
        super("messages.yml", "messages.yml", DaisyLib.getInstance());
        withDefaultUpdaterSettings()''
    }

    public static MessageConfig getInstance() {
        if (instance == null) {
            instance = new MessageConfig();
        }
        return instance;
    }

    public ComponentReplacer getPrefixReplacer() {
        return ComponentReplacer.componentReplacer("prefix", getPrefix().getMessage());
    }

    // GENERAL MESSAGES

    public ComponentMessage getPrefix() {
        return getComponentMessage("prefix", "<gray>[DaisyLib]</gray> ");
    }

    // MAIN COMMAND MESSAGES

    public ComponentMessage getMainUsageMessage() {
        return getComponentMessage("main-command.usage", "<aqua>Usage: /daisylib reload</aqua>")
                .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getReloadedMessage() {
        return getComponentMessage("main-command.reloaded", "<aqua>Successfully reloaded the plugin.</aqua>")
                .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getNoRewardTypesMessage() {
        return getComponentMessage("main-command.reward-types.none", "<aqua>There are no registered reward types.</aqua>")
                .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getListRewardTypesMessage() {
        return getComponentMessage("main-command.reward-types.list", "<aqua>Registered Reward Types:</aqua> <green>{list}</green>")
                .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getNoRequirementTypesMessage() {
        return getComponentMessage("main-command.requirement-types.none", "<aqua>There are no registered requirement types.</aqua>")
                .applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getListRequirementTypesMessage() {
        return getComponentMessage("main-command.requirement-types.list", "<aqua>Registered Requirement Types:</aqua> <green>{list}</green>")
                .applyReplacer(getPrefixReplacer());
    }

}

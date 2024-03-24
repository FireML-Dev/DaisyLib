package uk.firedev.daisylib.local;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import uk.firedev.daisylib.local.config.MessageConfig;

public class LibCommand extends CommandAPICommand {

    private static LibCommand instance = null;

    private LibCommand() {
        super("daisylib");
        setPermission(CommandPermission.fromString("daisylib.command"));
        withShortDescription("Manage the plugin");
        withFullDescription("Manage the plugin");
        withSubcommands(getReloadCommand());
        executes((sender, arguments) -> {
            MessageConfig.getInstance().sendPrefixedMessageFromConfig(sender, "messages.usage");
        });
    }

    public static LibCommand getInstance() {
        if (instance == null) {
            instance = new LibCommand();
        }
        return instance;
    }

    private CommandAPICommand getReloadCommand() {
        return new CommandAPICommand("reload")
                .executes(((sender, arguments) -> {
                    DaisyLib.getInstance().reload();
                    MessageConfig.getInstance().sendPrefixedMessageFromConfig(sender, "messages.reloaded");
                }));
    }

}

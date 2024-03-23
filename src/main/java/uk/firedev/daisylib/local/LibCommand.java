package uk.firedev.daisylib.local;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.command.Command;
import uk.firedev.daisylib.local.config.MessageConfig;

import java.util.List;

public class LibCommand implements Command {

    @Override
    public @NotNull String getName() {
        return "daisylib";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public @NotNull String getPermission() {
        return "daisylib.command";
    }

    @Override
    public CommandAPICommand[] getChildren() {
        return List.of(
                new ReloadSubCommand().getInstance()
        ).toArray(CommandAPICommand[]::new);
    }

    @Override
    public @NotNull String getShortDescription() {
        return "Manage the plugin";
    }

    @Override
    public @NotNull String getFullDescription() {
        return "Manage the plugin";
    }

    @Override
    public void execute(CommandSender sender, CommandArguments args) {}

    private static class ReloadSubCommand implements Command {

        @Override
        public @NotNull String getName() {
            return "reload";
        }

        @Override
        public String[] getAliases() {
            return new String[0];
        }

        @Override
        public @NotNull String getPermission() {
            return "";
        }

        @Override
        public CommandAPICommand[] getChildren() {
            return new CommandAPICommand[0];
        }

        @Override
        public @NotNull String getShortDescription() {
            return "Reload";
        }

        @Override
        public @NotNull String getFullDescription() {
            return "Reload";
        }

        @Override
        public void execute(CommandSender sender, CommandArguments args) {
            DaisyLib.getInstance().reload();
            MessageConfig.getInstance().sendPrefixedMessageFromConfig(sender, "messages.reloaded");
        }
    }

}

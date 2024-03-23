package uk.firedev.daisylib.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface Command {

    @NotNull String getName();

    @NotNull String[] getAliases();

    @NotNull String getPermission();

    @NotNull CommandAPICommand[] getChildren();

    @NotNull String getShortDescription();

    @NotNull String getFullDescription();

    void execute(CommandSender sender, CommandArguments args);


    default CommandAPICommand getInstance() {
        return new CommandAPICommand(getName())
                .withAliases(getAliases())
                .withPermission(getPermission())
                .withSubcommands(getChildren())
                .withShortDescription(getShortDescription())
                .withFullDescription(getFullDescription())
                .executes(this::execute)
                .instance();
    }

    default void register() {
        getInstance().register();
    }

}

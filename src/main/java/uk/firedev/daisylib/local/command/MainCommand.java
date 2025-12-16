package uk.firedev.daisylib.local.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.config.MessageConfig;

import static uk.firedev.daisylib.local.command.subcommand.ListSubcommand.list;

public class MainCommand {

    public static LiteralCommandNode<CommandSourceStack> get() {
        return Commands.literal("daisylib")
            .requires(stack -> stack.getSender().hasPermission("daisylib.command"))
            .executes(ctx -> {
                MessageConfig.getInstance().getMainUsageMessage().send(ctx.getSource().getSender());
                return 1;
            })
            .then(reload())
            .then(list())
            .build();
    }

    private static LiteralArgumentBuilder<CommandSourceStack> reload() {
        return Commands.literal("reload")
            .executes(ctx -> {
                DaisyLib.getInstance().reload();
                MessageConfig.getInstance().getReloadedMessage().send(ctx.getSource().getSender());
                return 1;
            });
    }


}

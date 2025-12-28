package uk.firedev.daisylib.internal.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.jetbrains.annotations.ApiStatus;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

import static uk.firedev.daisylib.internal.command.subcommand.ListSubcommand.list;

@ApiStatus.Internal
public class MainCommand {

    public static LiteralCommandNode<CommandSourceStack> get() {
        return Commands.literal("daisylib")
            .requires(stack -> stack.getSender().hasPermission("daisylib.command"))
            .then(reload())
            .then(list())
            .build();
    }

    private static LiteralArgumentBuilder<CommandSourceStack> reload() {
        return Commands.literal("reload")
            .executes(ctx -> {
                DaisyLibPlugin.getInstance().reload();
                // TODO send reloaded message to sender.
                return 1;
            });
    }

}

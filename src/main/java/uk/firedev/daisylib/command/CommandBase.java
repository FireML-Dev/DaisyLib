package uk.firedev.daisylib.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;

public abstract class CommandBase {

    private static final ComponentSingleMessage NO_ACCESS = ComponentMessage.componentMessage(
        "<red>You are not able to use this command!"
    );

    public void register(@NotNull Plugin plugin) {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands ->
            commands.registrar().register(getCommand())
        );
    }

    public boolean requirePermission(@Nullable String permission, @NotNull CommandSourceStack sender) {
        if (permission == null || permission.isEmpty()) {
            return true;
        }
        return sender.getSender().hasPermission(permission);
    }

    public abstract LiteralCommandNode<CommandSourceStack> getCommand();

}

package uk.firedev.daisylib.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CommandBase {

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

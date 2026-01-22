package uk.firedev.daisylib.internal.command.subcommand;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.addons.Addon;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.addons.action.ActionAddonRegistry;
import uk.firedev.daisylib.addons.item.ItemAddon;
import uk.firedev.daisylib.addons.item.ItemAddonRegistry;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementAddonRegistry;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.addons.reward.RewardAddonRegistry;
import uk.firedev.daisylib.internal.config.MessageConfig;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.Collection;
import java.util.List;

@ApiStatus.Internal
public class ListSubcommand {

    public static LiteralArgumentBuilder<CommandSourceStack> list() {
        return Commands.literal("list")
            .then(itemAddons())
            .then(rewardAddons())
            .then(requirementAddons())
            .then(actionAddons());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> itemAddons() {
        return buildListCommand("itemAddons", ItemAddon.class, ItemAddonRegistry.get().getRegistry().values());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> rewardAddons() {
        return buildListCommand("rewardAddons", RewardAddon.class, RewardAddonRegistry.get().getRegistry().values());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> requirementAddons() {
        return buildListCommand("requirementAddons", RequirementAddon.class, RequirementAddonRegistry.get().getRegistry().values());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> actionAddons() {
        return buildListCommand("actionAddons", ActionAddon.class, ActionAddonRegistry.get().getRegistry().values());
    }

    private static <T extends Addon> LiteralArgumentBuilder<CommandSourceStack> buildListCommand(@NonNull String name, @NonNull Class<T> clazz, @NonNull Collection<? extends T> values) {
        return Commands.literal(name)
            .executes(ctx -> {
                CommandSender sender = ctx.getSource().getSender();
                // TODO send messages
                if (values.isEmpty()) {
                    sender.sendPlainMessage("No addons :(");
                    //MessageConfig.getInstance().getNoAddonsMessage(clazz).send(sender);
                } else {
                    ComponentMessage.componentMessage("<#F0E68C>Registered {name}s: <green>{list}</green>")
                        .replace("{name}", clazz.getSimpleName())
                        .replace(getAddonListReplacer(values))
                        .send(sender);
                }
                return 1;
            });
    }

    private static Replacer getAddonListReplacer(Collection<? extends Addon> types) {
        // Gather all types in their intended format
        List<Component> typeComponents = types.stream()
            .map(addon -> {
                Component identifier = ComponentMessage.componentMessage(addon.getKey()).get();
                identifier = identifier.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                    ComponentMessage.componentMessage(
                        "<white>Author: " + addon.getAuthor() + "\n" +
                            "<white>Plugin: " + addon.getPlugin().getName()
                    ).get()
                ));
                return identifier;
            })
            .toList();

        // Join the formatted types together with a comma
        Component joined = Component.join(
            JoinConfiguration.commas(true),
            typeComponents
        );

        return Replacer.replacer().addReplacement("{list}", joined);
    }

}

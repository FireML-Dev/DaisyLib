package uk.firedev.daisylib.local.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.CommandSender;
import uk.firedev.daisylib.addons.Addon;
import uk.firedev.daisylib.addons.item.ItemAddon;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.command.CommandBase;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.Collection;
import java.util.List;

public class LibCommand extends CommandBase {

    @Override
    public LiteralCommandNode<CommandSourceStack> getCommand() {
        return Commands.literal("daisylib")
            .requires(sender -> requirePermission("daisylib.command", sender))
            .executes(ctx -> {
                MessageConfig.getInstance().getMainUsageMessage().send(ctx.getSource().getSender());
                return Command.SINGLE_SUCCESS;
            })
            .then(reload())
            .then(list())
            .build();
    }

    private LiteralArgumentBuilder<CommandSourceStack> reload() {
        return Commands.literal("reload")
            .executes(ctx -> {
                DaisyLib.getInstance().reload();
                MessageConfig.getInstance().getReloadedMessage().send(ctx.getSource().getSender());
                return Command.SINGLE_SUCCESS;
            });
    }

    private LiteralArgumentBuilder<CommandSourceStack> list() {
        return Commands.literal("list")
            .then(listItemAddons())
            .then(listRewardAddons())
            .then(listRequirementAddons());
    }

    private LiteralArgumentBuilder<CommandSourceStack> listItemAddons() {
        return Commands.literal("itemAddons")
            .executes(ctx -> {
                CommandSender sender = ctx.getSource().getSender();
                Collection<ItemAddon> registered = ItemAddon.getRegistry().values();
                if (registered.isEmpty()) {
                    MessageConfig.getInstance().getNoAddonsMessage(ItemAddon.class).send(sender);
                } else {
                    MessageConfig.getInstance().getListAddonsMessage(ItemAddon.class)
                        .replace(getAddonListReplacer(registered))
                        .send(sender);
                }
                return Command.SINGLE_SUCCESS;
            });
    }

    private LiteralArgumentBuilder<CommandSourceStack> listRewardAddons() {
        return Commands.literal("rewardAddons")
            .executes(ctx -> {
                CommandSender sender = ctx.getSource().getSender();
                Collection<RewardAddon> registered = RewardAddon.getRegistry().values();
                if (registered.isEmpty()) {
                    MessageConfig.getInstance().getNoAddonsMessage(RewardAddon.class).send(sender);
                } else {
                    MessageConfig.getInstance().getListAddonsMessage(RewardAddon.class)
                        .replace(getAddonListReplacer(registered))
                        .send(sender);
                }
                return Command.SINGLE_SUCCESS;
            });
    }

    private LiteralArgumentBuilder<CommandSourceStack> listRequirementAddons() {
        return Commands.literal("requirementAddons")
            .executes(ctx -> {
                CommandSender sender = ctx.getSource().getSender();
                Collection<RequirementAddon> registered = RequirementAddon.getRegistry().values();
                if (registered.isEmpty()) {
                    MessageConfig.getInstance().getNoAddonsMessage(RequirementAddon.class).send(sender);
                } else {
                    MessageConfig.getInstance().getListAddonsMessage(RequirementAddon.class)
                        .replace(getAddonListReplacer(registered))
                        .send(sender);
                }
                return Command.SINGLE_SUCCESS;
            });
    }

    private Replacer getAddonListReplacer(Collection<? extends Addon> types) {
        // Gather all types in their intended format
        List<Component> typeComponents = types.stream()
                .map(rewardType -> {
                    Component identifier = ComponentMessage.componentMessage(rewardType.getIdentifier()).get();
                    identifier = identifier.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                            ComponentMessage.componentMessage(
                                    "<white>Author: " + rewardType.getAuthor() + "\n" +
                                    "<white>Registered Plugin: " + rewardType.getOwningPlugin().getName()
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

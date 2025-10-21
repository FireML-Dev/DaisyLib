package uk.firedev.daisylib.local.command;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import uk.firedev.daisylib.addons.Addon;
import uk.firedev.daisylib.addons.item.ItemAddon;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.Collection;
import java.util.List;

public class LibCommand {

    private LibCommand() {}

    public static CommandTree getCommand() {
        return new CommandTree("daisylib")
            .withPermission("daisylib.command")
            .withFullDescription("Manage the plugin")
            .withShortDescription("Manage the plugin")
            .executes(info -> {
                MessageConfig.getInstance().getMainUsageMessage().send(info.sender());
            })
            .then(getReloadBranch())
            .then(getListBranch());
    }

    private static Argument<String> getReloadBranch() {
        return new LiteralArgument("reload")
                .executes(((sender, arguments) -> {
                    DaisyLib.getInstance().reload();
                    MessageConfig.getInstance().getReloadedMessage().send(sender);
                }));
    }

    private static Argument<String> getListBranch() {
        return new LiteralArgument("list")
            .then(getItemAddonsBranch())
            .then(getRequirementAddonsBranch())
            .then(getRewardAddonsBranch());
    }

    private static Argument<String> getItemAddonsBranch() {
        return new LiteralArgument("itemAddons")
            .executes(info -> {
                Collection<ItemAddon> registered = ItemAddon.getRegistry().values();
                if (registered.isEmpty()) {
                    MessageConfig.getInstance().getNoAddonsMessage(ItemAddon.class).send(info.sender());
                } else {
                    MessageConfig.getInstance().getListAddonsMessage(ItemAddon.class)
                        .replace(getAddonListReplacer(registered))
                        .send(info.sender());
                }
            });
    }

    private static Argument<String> getRewardAddonsBranch() {
        return new LiteralArgument("rewardAddons")
                .executes(info -> {
                    Collection<RewardAddon> registered = RewardAddon.getRegistry().values();
                    if (registered.isEmpty()) {
                        MessageConfig.getInstance().getNoAddonsMessage(RewardAddon.class).send(info.sender());
                    } else {
                        MessageConfig.getInstance().getListAddonsMessage(RewardAddon.class)
                            .replace(getAddonListReplacer(registered))
                            .send(info.sender());
                    }
                });
    }

    private static Argument<String> getRequirementAddonsBranch() {
        return new LiteralArgument("requirementAddons")
            .executes(info -> {
                Collection<RequirementAddon> registered = RequirementAddon.getRegistry().values();
                if (registered.isEmpty()) {
                    MessageConfig.getInstance().getNoAddonsMessage(RequirementAddon.class).send(info.sender());
                } else {
                    MessageConfig.getInstance().getListAddonsMessage(RequirementAddon.class)
                        .replace(getAddonListReplacer(registered))
                        .send(info.sender());
                }
            });
    }

    private static Replacer getAddonListReplacer(Collection<? extends Addon> types) {
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

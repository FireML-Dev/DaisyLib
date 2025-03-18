package uk.firedev.daisylib.local.command;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.executors.CommandExecutor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import uk.firedev.daisylib.api.addons.Addon;
import uk.firedev.daisylib.api.addons.item.ItemAddon;
import uk.firedev.daisylib.api.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.api.addons.reward.RewardAddon;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.message.component.ComponentReplacer;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.config.MessageConfig;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LibCommand {

    private static CommandTree command = null;

    private LibCommand() {}

    public static CommandTree getCommand() {
        if (command == null) {
            command = new CommandTree("daisylib")
                    .withPermission("daisylib.command")
                    .withFullDescription("Manage the plugin")
                    .withShortDescription("Manage the plugin")
                    .executes((CommandExecutor) (sender, args) -> MessageConfig.getInstance().getMainUsageMessage().sendMessage(sender))
                    .then(getReloadBranch())
                    .then(getListBranch());
        }
        return command;
    }

    private static Argument<String> getReloadBranch() {
        return new LiteralArgument("reload")
                .executes(((sender, arguments) -> {
                    DaisyLib.getInstance().reload();
                    MessageConfig.getInstance().getReloadedMessage().sendMessage(sender);
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
                Collection<ItemAddon> registered = ItemAddon.getLoadedAddons().values();
                if (registered.isEmpty()) {
                    MessageConfig.getInstance().getNoAddonsMessage(ItemAddon.class).sendMessage(info.sender());
                } else {
                    MessageConfig.getInstance().getListAddonsMessage(RequirementAddon.class)
                        .applyReplacer(getAddonListReplacer(registered))
                        .sendMessage(info.sender());
                }
            });
    }

    private static Argument<String> getRewardAddonsBranch() {
        return new LiteralArgument("rewardAddons")
                .executes(info -> {
                    Collection<RewardAddon> registered = RewardAddon.getLoadedAddons().values();
                    if (registered.isEmpty()) {
                        MessageConfig.getInstance().getNoAddonsMessage(RewardAddon.class).sendMessage(info.sender());
                    } else {
                        MessageConfig.getInstance().getListAddonsMessage(RequirementAddon.class)
                            .applyReplacer(getAddonListReplacer(registered))
                            .sendMessage(info.sender());
                    }
                });
    }

    private static Argument<String> getRequirementAddonsBranch() {
        return new LiteralArgument("requirementAddons")
            .executes(info -> {
                Collection<RequirementAddon> registered = RequirementAddon.getLoadedAddons().values();
                if (registered.isEmpty()) {
                    MessageConfig.getInstance().getNoAddonsMessage(RequirementAddon.class).sendMessage(info.sender());
                } else {
                    MessageConfig.getInstance().getListAddonsMessage(RequirementAddon.class)
                        .applyReplacer(getAddonListReplacer(registered))
                        .sendMessage(info.sender());
                }
            });
    }

    private static ComponentReplacer getAddonListReplacer(Collection<? extends Addon> types) {
        // Gather all types in their intended format
        List<Component> typeComponents = types.stream()
                .map(rewardType -> {
                    Component identifier = ComponentMessage.fromString(rewardType.getIdentifier()).getMessage();
                    identifier = identifier.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                            ComponentMessage.fromString(
                                    "<white>Author: " + rewardType.getAuthor() + "\n" +
                                            "<white>Registered Plugin: " + rewardType.getOwningPlugin().getName()
                            ).getMessage()
                    ));
                    return identifier;
                })
                .toList();

        // Join the formatted types together with a comma
        Component joined = Component.join(
                JoinConfiguration.commas(true),
                typeComponents
        );

        return ComponentReplacer.create("list", joined);
    }

}

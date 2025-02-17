package uk.firedev.daisylib.local.command;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.executors.CommandExecutor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.message.component.ComponentReplacer;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.daisylib.requirement.RequirementManager;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.reward.RewardManager;
import uk.firedev.daisylib.reward.RewardType;

import java.util.List;

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
                    .then(getRewardTypesBranch())
                    .then(getRequirementTypesBranch());
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

    private static Argument<String> getRewardTypesBranch() {
        return new LiteralArgument("rewardTypes")
                .executes((sender, arguments) -> {
                    List<RewardType> registeredTypes = RewardManager.getInstance().getRegisteredRewardTypes();
                    if (registeredTypes.isEmpty()) {
                        MessageConfig.getInstance().getNoRewardTypesMessage().sendMessage(sender);
                    } else {
                        MessageConfig.getInstance().getListRewardTypesMessage()
                                .applyReplacer(getRewardTypeListReplacer(registeredTypes))
                                .sendMessage(sender);
                    }
                });
    }

    private static ComponentReplacer getRewardTypeListReplacer(List<RewardType> types) {

        // Gather all reward types in their intended format
        List<Component> typeComponents = types.stream()
                .map(rewardType -> {
                    Component identifier = ComponentMessage.fromString(rewardType.getIdentifier()).getMessage();
                    identifier = identifier.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                            ComponentMessage.fromString(
                                    "<white>Author: " + rewardType.getAuthor() + "\n" +
                                            "<white>Registered Plugin: " + rewardType.getPlugin().getName()
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

    private static Argument<String> getRequirementTypesBranch() {
        return new LiteralArgument("requirementTypes")
                .executes((sender, arguments) -> {
                    List<RequirementType> registeredTypes = RequirementManager.getInstance().getRegisteredRequirementTypes();
                    if (registeredTypes.isEmpty()) {
                        MessageConfig.getInstance().getNoRequirementTypesMessage().sendMessage(sender);
                    } else {
                        MessageConfig.getInstance().getListRequirementTypesMessage()
                                .applyReplacer(getRequirementTypeListReplacer(registeredTypes))
                                .sendMessage(sender);
                    }
                });
    }

    private static ComponentReplacer getRequirementTypeListReplacer(List<RequirementType> types) {

        // Gather all requirement types in their intended format
        List<Component> typeComponents = types.stream()
                .map(requirementType -> {
                    Component identifier = ComponentMessage.fromString(requirementType.getIdentifier()).getMessage();
                    identifier = identifier.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                            ComponentMessage.fromString(
                                    "<white>Author: " + requirementType.getAuthor() + "\n" +
                                            "<white>Registered Plugin: " + requirementType.getPlugin().getName()
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

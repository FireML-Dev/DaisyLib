package uk.firedev.daisylib.local.command;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.command.subcommand.TestSubCommand;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.message.component.ComponentReplacer;
import uk.firedev.daisylib.requirement.RequirementManager;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.reward.RewardManager;
import uk.firedev.daisylib.reward.RewardType;

import java.util.List;

public class LibCommand {

    private static CommandAPICommand command = null;

    private LibCommand() {}

    public static CommandAPICommand getCommand() {
        if (command == null) {
            command = new CommandAPICommand("daisylib")
                    .withPermission("daisylib.command")
                    .withFullDescription("Manage the plugin")
                    .withShortDescription("Manage the plugin")
                    .withSubcommands(getReloadCommand(), getRewardTypesCommand(), getRequirementTypesCommand(), TestSubCommand.getCommand())
                    .executes((sender, args) -> {
                        MessageConfig.getInstance().getMainUsageMessage().sendMessage(sender);
                    });
        }
        return command;
    }

    private static CommandAPICommand getReloadCommand() {
        return new CommandAPICommand("reload")
                .executes(((sender, arguments) -> {
                    DaisyLib.getInstance().reload();
                    MessageConfig.getInstance().getReloadedMessage().sendMessage(sender);
                }));
    }

    private static CommandAPICommand getRewardTypesCommand() {
        return new CommandAPICommand("rewardTypes")
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
        TextComponent.Builder builder = Component.text();
        types.forEach(rewardType -> {
            Component identifier = ComponentMessage.fromString(rewardType.getIdentifier()).getMessage();
            identifier = identifier.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                    ComponentMessage.fromString(
                            "<white>Author: " + rewardType.getAuthor() + "\n" +
                            "<white>Registered Plugin: " + rewardType.getPlugin().getName()
                    ).getMessage()
            ));
            builder.append(identifier, Component.text(", "));
        });
        return ComponentReplacer.componentReplacer("list", builder.build());
    }

    private static CommandAPICommand getRequirementTypesCommand() {
        return new CommandAPICommand("requirementTypes")
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
        TextComponent.Builder builder = Component.text();
        types.forEach(rewardType -> {
            Component identifier = ComponentMessage.fromString(rewardType.getIdentifier()).getMessage();
            identifier = identifier.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                    ComponentMessage.fromString(
                            "<white>Author: " + rewardType.getAuthor() + "\n" +
                                    "<white>Registered Plugin: " + rewardType.getPlugin().getName()
                    ).getMessage()
            ));
            builder.append(identifier, Component.text(", "));
        });
        return ComponentReplacer.componentReplacer("list", builder.build());
    }

}

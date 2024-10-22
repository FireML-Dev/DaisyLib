package uk.firedev.daisylib.local.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.command.subcommand.Test;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;
import uk.firedev.daisylib.requirement.RequirementManager;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.reward.RewardManager;
import uk.firedev.daisylib.reward.RewardType;

import java.util.List;

public class LibCommand extends CommandAPICommand {

    private static LibCommand instance = null;

    private LibCommand() {
        super("daisylib");
        setPermission(CommandPermission.fromString("daisylib.command"));
        withShortDescription("Manage the plugin");
        withFullDescription("Manage the plugin");
        withSubcommands(getReloadCommand(), getRewardTypesCommand(), getRequirementTypesCommand(), Test.getInstance());
        executes((sender, arguments) -> {
            MessageConfig.getInstance().getMainUsageMessage().sendMessage(sender);
        });
    }

    public static LibCommand getInstance() {
        if (instance == null) {
            instance = new LibCommand();
        }
        return instance;
    }

    private CommandAPICommand getReloadCommand() {
        return new CommandAPICommand("reload")
                .executes(((sender, arguments) -> {
                    DaisyLib.getInstance().reload();
                    MessageConfig.getInstance().getReloadedMessage().sendMessage(sender);
                }));
    }

    private CommandAPICommand getRewardTypesCommand() {
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

    private ComponentReplacer getRewardTypeListReplacer(List<RewardType> types) {
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

    private CommandAPICommand getRequirementTypesCommand() {
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

    private ComponentReplacer getRequirementTypeListReplacer(List<RequirementType> types) {
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

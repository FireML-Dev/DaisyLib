package uk.firedev.daisylib.local;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.daisylib.reward.RewardManager;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.ComponentUtils;

import java.util.List;
import java.util.Map;

public class LibCommand extends CommandAPICommand {

    private static LibCommand instance = null;

    private LibCommand() {
        super("daisylib");
        setPermission(CommandPermission.fromString("daisylib.command"));
        withShortDescription("Manage the plugin");
        withFullDescription("Manage the plugin");
        withSubcommands(getReloadCommand(), getRewardTypesCommand());
        executes((sender, arguments) -> {
            MessageConfig.getInstance().sendPrefixedMessageFromConfig(sender, "messages.main-command.usage");
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
                    MessageConfig.getInstance().sendPrefixedMessageFromConfig(sender, "messages.main-command.reloaded");
                }));
    }

    private CommandAPICommand getRewardTypesCommand() {
        return new CommandAPICommand("rewardTypes")
                .executes((sender, arguments) -> {
                    List<RewardType> registeredTypes = RewardManager.getInstance().getRegisteredRewardTypes();
                    if (registeredTypes.isEmpty()) {
                        MessageConfig.getInstance().sendPrefixedMessageFromConfig(sender, "messages.main-command.reward-types.none");
                    } else {
                        MessageConfig.getInstance().sendPrefixedMessage(sender, getRewardTypeList(registeredTypes));
                    }
                });
    }

    private Component getRewardTypeList(List<RewardType> types) {
        Component message = MessageConfig.getInstance().getConfig().getRichMessage("messages.main-command.reward-types.list");
        TextComponent.Builder builder = Component.text();
        types.forEach(rewardType -> {
            Component identifier = ComponentUtils.deserializeString(rewardType.getIdentifier());
            identifier = identifier.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                    ComponentUtils.deserializeString(
                            "<white>Author: " + rewardType.getAuthor() + "\n" +
                            "<white>Registered Plugin: " + rewardType.getPlugin().getName()
                    )
            ));
            builder.append(identifier, Component.text(", "));
        });
        message = ComponentUtils.parsePlaceholders(message,
                Map.of("list", builder.build())
        );
        return message;
    }

}

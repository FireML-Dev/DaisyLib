package uk.firedev.daisylib.local;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import uk.firedev.daisylib.local.config.MainConfig;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.daisylib.message.Message;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;
import uk.firedev.daisylib.reward.RewardManager;
import uk.firedev.daisylib.reward.RewardType;

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
            new ComponentMessage(
                    MainConfig.getInstance().getConfig(),
                    "messages.main-command.usage",
                    "<aqua>Usage: /daisylib reload</aqua>"
            ).addPrefix(MessageConfig.getInstance().getPrefix())
            .sendMessage(sender);
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
                    new ComponentMessage(
                            MainConfig.getInstance().getConfig(),
                            "messages.main-command.reloaded",
                            "<aqua>Successfully reloaded the plugin.</aqua>"
                    ).addPrefix(MessageConfig.getInstance().getPrefix())
                    .sendMessage(sender);
                }));
    }

    private CommandAPICommand getRewardTypesCommand() {
        return new CommandAPICommand("rewardTypes")
                .executes((sender, arguments) -> {
                    List<RewardType> registeredTypes = RewardManager.getInstance().getRegisteredRewardTypes();
                    if (registeredTypes.isEmpty()) {
                        new ComponentMessage(
                                MainConfig.getInstance().getConfig(),
                                "messages.main-command.reward-types.none",
                                "<aqua>There are no registered reward types.</aqua>"
                        ).addPrefix(MessageConfig.getInstance().getPrefix())
                        .sendMessage(sender);
                    } else {
                        getRewardTypeList(registeredTypes)
                            .addPrefix(MessageConfig.getInstance().getPrefix())
                            .sendMessage(sender);
                    }
                });
    }

    private ComponentMessage getRewardTypeList(List<RewardType> types) {
        ComponentMessage message = new ComponentMessage(
                MessageConfig.getInstance().getConfig(),
                "messages.main-command.reward-types.list",
                "<aqua>Registered Reward Types:</aqua> <green>{list}</green>"
        );
        TextComponent.Builder builder = Component.text();
        types.forEach(rewardType -> {
            Component identifier = new ComponentMessage(rewardType.getIdentifier()).getMessage();
            identifier = identifier.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentMessage(
                            "<white>Author: " + rewardType.getAuthor() + "\n" +
                            "<white>Registered Plugin: " + rewardType.getPlugin().getName()
                    ).getMessage()
            ));
            builder.append(identifier, Component.text(", "));
        });
        ComponentReplacer replacer = new ComponentReplacer().addReplacement("list", builder.build());
        message = message.applyReplacer(replacer);
        return message;
    }

}

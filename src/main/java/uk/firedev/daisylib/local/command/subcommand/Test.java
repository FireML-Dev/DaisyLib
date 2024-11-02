package uk.firedev.daisylib.local.command.subcommand;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import uk.firedev.daisylib.command.ArgumentBuilder;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.requirement.RequirementData;
import uk.firedev.daisylib.requirement.RequirementManager;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.reward.RewardManager;
import uk.firedev.daisylib.reward.RewardType;

import java.util.List;
import java.util.Objects;

public class Test extends CommandAPICommand {

    private static Test instance = null;

    private Test() {
        super("test");
        withSubcommands(getRewardTypeCommand(), getRequirementTypeCommand());
    }

    public static Test getInstance() {
        if (instance == null) {
            instance = new Test();
        }
        return instance;
    }

    private CommandAPICommand getRewardTypeCommand() {
        CommandAPICommand command = new CommandAPICommand("rewardType");
        command.withArguments(
                ArgumentBuilder.getAsyncStringsArgument("type", info -> RewardManager.getInstance().getRewardTypeMap().keySet().toArray(String[]::new)),
                new StringArgument("value")
        );
        command.executesPlayer((player, arguments) -> {
            String type = (String) Objects.requireNonNull(arguments.get("type"));
            String value = (String) Objects.requireNonNull(arguments.get("value"));
            RewardType rewardType = RewardManager.getInstance().getRewardTypeMap().get(type);
            if (rewardType == null) {
                ComponentMessage.fromString("<red>" + type + " is not a valid RewardType.").sendMessage(player);
                return;
            }
            rewardType.doReward(player, value);
        });
        return command;
    }

    private CommandAPICommand getRequirementTypeCommand() {
        CommandAPICommand command = new CommandAPICommand("requirementType");
        command.withArguments(
                ArgumentBuilder.getAsyncStringsArgument("type", info -> RequirementManager.getInstance().getRegisteredRequirements().keySet().toArray(String[]::new)),
                new GreedyStringArgument("value")
        );
        command.executesPlayer((player, arguments) -> {
            String type = (String) Objects.requireNonNull(arguments.get("type"));
            String valueGreedy = (String) Objects.requireNonNull(arguments.get("value"));
            List<String> values = List.of(valueGreedy.split(" "));
            RequirementType requirementType = RequirementManager.getInstance().getRegisteredRequirements().get(type);
            if (requirementType == null) {
                ComponentMessage.fromString("<red>" + type + " is not a valid RequirementType.").sendMessage(player);
                return;
            }
            if (requirementType.checkRequirement(new RequirementData().withPlayer(player), values)) {
                ComponentMessage.fromString("<green>Test Success!").sendMessage(player);
            } else {
                ComponentMessage.fromString("<red>Test Failed!").sendMessage(player);
            }
        });
        return command;
    }

}

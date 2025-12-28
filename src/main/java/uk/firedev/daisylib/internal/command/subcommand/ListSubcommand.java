package uk.firedev.daisylib.internal.command.subcommand;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

// TODO this entire subcommand
public class ListSubcommand {

    public static LiteralArgumentBuilder<CommandSourceStack> list() {
        return Commands.literal("list");
            //.then(itemAddons())
            //.then(rewardAddons())
            //.then(requirementAddons())
            //.then(actionAddons());
    }

    /*
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

    private static <T extends Addon> LiteralArgumentBuilder<CommandSourceStack> buildListCommand(@NotNull String name, @NotNull Class<T> clazz, @NotNull Collection<? extends T> values) {
        return Commands.literal(name)
            .executes(ctx -> {
                CommandSender sender = ctx.getSource().getSender();
                if (values.isEmpty()) {
                    MessageConfig.getInstance().getNoAddonsMessage(clazz).send(sender);
                } else {
                    MessageConfig.getInstance().getListAddonsMessage(clazz)
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
     */

}

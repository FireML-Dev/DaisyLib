package uk.firedev.daisylib.local.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import net.strokkur.commands.annotations.Command;
import net.strokkur.commands.annotations.Description;
import net.strokkur.commands.annotations.Executes;
import net.strokkur.commands.annotations.Permission;
import net.strokkur.commands.annotations.Subcommand;
import org.bukkit.command.CommandSender;
import uk.firedev.daisylib.addons.Addon;
import uk.firedev.daisylib.addons.item.ItemAddon;
import uk.firedev.daisylib.addons.item.ItemAddonRegistry;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementAddonRegistry;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.addons.reward.RewardAddonRegistry;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.Collection;
import java.util.List;

@Command("daisylib")
@Permission("daisylib.command")
@Description("Manage the plugin")
public class LibCommand {

    @Executes
    void onExecute(CommandSender sender) {
        MessageConfig.getInstance().getMainUsageMessage().send(sender);
    }

    @Executes("reload")
    void reload(CommandSender sender) {
        DaisyLib.getInstance().reload();
        MessageConfig.getInstance().getReloadedMessage().send(sender);
    }

    @Subcommand("list")
    static class ListSubcommand {

        @Executes("itemAddons")
        void itemAddons(CommandSender sender) {
            Collection<ItemAddon> registered = ItemAddonRegistry.get().getRegistry().values();
            if (registered.isEmpty()) {
                MessageConfig.getInstance().getNoAddonsMessage(ItemAddon.class).send(sender);
            } else {
                MessageConfig.getInstance().getListAddonsMessage(ItemAddon.class)
                    .replace(getAddonListReplacer(registered))
                    .send(sender);
            }
        }

        @Executes("rewardAddons")
        void rewardAddons(CommandSender sender) {
            Collection<RewardAddon> registered = RewardAddonRegistry.get().getRegistry().values();
            if (registered.isEmpty()) {
                MessageConfig.getInstance().getNoAddonsMessage(RewardAddon.class).send(sender);
            } else {
                MessageConfig.getInstance().getListAddonsMessage(RewardAddon.class)
                    .replace(getAddonListReplacer(registered))
                    .send(sender);
            }
        }

        @Executes("requirementAddons")
        void requirementAddons(CommandSender sender) {
            Collection<RequirementAddon> registered = RequirementAddonRegistry.get().getRegistry().values();
            if (registered.isEmpty()) {
                MessageConfig.getInstance().getNoAddonsMessage(RequirementAddon.class).send(sender);
            } else {
                MessageConfig.getInstance().getListAddonsMessage(RequirementAddon.class)
                    .replace(getAddonListReplacer(registered))
                    .send(sender);
            }
        }

        private Replacer getAddonListReplacer(Collection<? extends Addon> types) {
            // Gather all types in their intended format
            List<Component> typeComponents = types.stream()
                .map(rewardType -> {
                    Component identifier = ComponentMessage.componentMessage(rewardType.getKey()).get();
                    identifier = identifier.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                        ComponentMessage.componentMessage(
                            "<white>Author: " + rewardType.getAuthor() + "\n" +
                                "<white>Registered Plugin: " + rewardType.getPlugin().getName()
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


}

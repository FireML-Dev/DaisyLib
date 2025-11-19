package uk.firedev.daisylib.local.command;

import net.kyori.adventure.dialog.DialogLike;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import net.strokkur.commands.annotations.Command;
import net.strokkur.commands.annotations.Description;
import net.strokkur.commands.annotations.Executes;
import net.strokkur.commands.annotations.Permission;
import net.strokkur.commands.annotations.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.firedev.daisylib.addons.Addon;
import uk.firedev.daisylib.addons.action.ActionAddon;
import uk.firedev.daisylib.addons.action.ActionAddonRegistry;
import uk.firedev.daisylib.addons.item.ItemAddon;
import uk.firedev.daisylib.addons.item.ItemAddonRegistry;
import uk.firedev.daisylib.addons.requirement.RequirementAddon;
import uk.firedev.daisylib.addons.requirement.RequirementAddonRegistry;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.addons.reward.RewardAddonRegistry;
import uk.firedev.daisylib.builders.dialog.DialogBuilder;
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

        // TESTING SHENANIGANS
        if (!(sender instanceof Player player)) {
            return;
        }
        DialogLike dialog = DialogBuilder.information()
            .withTitle("Test Title")
            .addContent("I am a nerd.")
            .addContent("This is a variable: {e}")
            .addReplacement("{e}", "nuh uh")
            .build();
        player.showDialog(dialog);
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

        @Executes("actionAddons")
        void actionAddons(CommandSender sender) {
            Collection<ActionAddon<?>> registered = ActionAddonRegistry.get().getRegistry().values();
            if (registered.isEmpty()) {
                MessageConfig.getInstance().getNoAddonsMessage(ActionAddon.class).send(sender);
            } else {
                MessageConfig.getInstance().getListAddonsMessage(ActionAddon.class)
                    .replace(getAddonListReplacer(registered))
                    .send(sender);
            }
        }

        private Replacer getAddonListReplacer(Collection<? extends Addon> types) {
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

    }


}

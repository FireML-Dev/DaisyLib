package uk.firedev.daisylib.local;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.local.config.MessageConfig;

import java.util.ArrayList;
import java.util.List;

public class LibCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length >= 1) {
            switch (args[0]) {
                case "reload" -> {
                    DaisyLib.getInstance().reload();
                    MessageConfig.getInstance().sendPrefixedMessageFromConfig(sender, "messages.reloaded");
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggests = new ArrayList<>();
        List<String> finalSuggests = new ArrayList<>();
        if (args.length == 1) {
            suggests.add("reload");
            StringUtil.copyPartialMatches(args[0], suggests, finalSuggests);
            return finalSuggests;
        }
        return finalSuggests;
    }

}

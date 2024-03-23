package uk.firedev.daisylib.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public interface ICommand extends CommandExecutor, TabCompleter {

    default List<String> processTabCompletions(String arg, List<String> choices) {
        List<String> toReturn = new ArrayList<>();
        StringUtil.copyPartialMatches(arg, choices, toReturn);
        return toReturn;
    }

    default boolean registerCommand(String name, JavaPlugin plugin) {
        PluginCommand pluginCommand = plugin.getCommand(name);
        if (pluginCommand == null) {
            return false;
        }
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);
        return true;
    }

}

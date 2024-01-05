package uk.firedev.daisylib.local.config;

import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.Config;

public class MessageManager extends Config {

    private static MessageManager instance = null;

    public MessageManager(String fileName, JavaPlugin plugin) {
        super(fileName, plugin);
        instance = this;
    }

    public static MessageManager getInstance() {
        return instance;
    }

}

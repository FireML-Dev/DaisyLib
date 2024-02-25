package uk.firedev.daisylib.local.config;

import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.Config;

public class ConfigManager extends Config {

    private static ConfigManager instance = null;

    public ConfigManager(String fileName, JavaPlugin plugin) {
        super(fileName, plugin);
        instance = this;
    }

    public static ConfigManager getInstance() { return instance; }

    public boolean doMoveBlockEvent() { return getConfig().getBoolean("config.custom-events.move-block", true); }

    public boolean doMoveChunkEvent() { return getConfig().getBoolean("config.custom-events.move-chunk", true); }

    public boolean doPlaceBreak() { return getConfig().getBoolean("config.enablePlaceBreak", true);  }

}

package uk.firedev.daisylib.local.config;

import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.Config;

public class ConfigManager extends Config {

    public boolean doMoveBlockEvent = false;
    public boolean doMoveChunkEvent = false;

    private static ConfigManager instance = null;

    public ConfigManager(String fileName, JavaPlugin plugin) {
        super(fileName, plugin);
        instance = this;
    }

    @Override
    public void reload() {
        super.reload();
        this.doMoveBlockEvent = getConfig().getBoolean("config.custom-events.move-block");
        this.doMoveChunkEvent = getConfig().getBoolean("config.custom-events.move-chunk");
    }

    public static ConfigManager getInstance() {
        return instance;
    }

}

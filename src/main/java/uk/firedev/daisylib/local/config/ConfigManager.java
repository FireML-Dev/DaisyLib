package uk.firedev.daisylib.local.config;

import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.Config;

public class ConfigManager extends Config {

    public boolean doMoveBlockEvent = false;
    public boolean doMoveChunkEvent = false;
    public boolean doPlaceBreak = false;

    private static ConfigManager instance = null;

    public ConfigManager(String fileName, JavaPlugin plugin) {
        super(fileName, plugin);
        setVariables();
        instance = this;
    }

    @Override
    public void reload() {
        super.reload();
        setVariables();
    }

    public static ConfigManager getInstance() {
        return instance;
    }

    private void setVariables() {
        this.doMoveBlockEvent = getConfig().getBoolean("config.custom-events.move-block", true);
        this.doMoveChunkEvent = getConfig().getBoolean("config.custom-events.move-chunk", true);
        this.doPlaceBreak = getConfig().getBoolean("config.enablePlaceBreak", true);
    }

}

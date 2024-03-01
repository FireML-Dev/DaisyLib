package uk.firedev.daisylib.local;

import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.events.DaisyLibReloadEvent;
import uk.firedev.daisylib.utils.BlockUtils;
import uk.firedev.daisylib.local.config.MainConfig;
import uk.firedev.daisylib.local.config.MessageConfig;

import java.util.logging.Level;

public final class DaisyLib extends JavaPlugin {

    private static DaisyLib instance;
    private static TaskScheduler scheduler;
    public boolean papiEnabled;
    public boolean denizenEnabled;

    @Override
    public void onEnable() {
        try {
            Class.forName("com.destroystokyo.paper.profile.PlayerProfile");
        } catch (ClassNotFoundException ex) {
            Loggers.log(Level.SEVERE, getLogger(), "Paper not found. Please run this plugin on a Paper server!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;
        scheduler = UniversalScheduler.getScheduler(this);
        reload();
        this.getCommand("daisylib").setExecutor(new LibCommand());
        this.getCommand("daisylib").setTabCompleter(new LibCommand());
        registerListeners();
    }

    @Override
    public void onDisable() {}

    public void reload() {
        MainConfig.getInstance().reload();
        MessageConfig.getInstance().reload();
        Bukkit.getPluginManager().callEvent(new DaisyLibReloadEvent());
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new BlockUtils(), this);
    }

    public static DaisyLib getInstance() { return instance; }

    public static TaskScheduler getScheduler() { return scheduler; }

}

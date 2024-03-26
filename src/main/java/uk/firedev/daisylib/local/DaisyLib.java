package uk.firedev.daisylib.local;

import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.events.DaisyLibReloadEvent;
import uk.firedev.daisylib.local.config.MainConfig;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.daisylib.reward.RewardManager;
import uk.firedev.daisylib.utils.BlockUtils;

public final class DaisyLib extends JavaPlugin {

    private static DaisyLib instance;
    private static TaskScheduler scheduler;
    public boolean papiEnabled;
    public boolean denizenEnabled;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this)
                .shouldHookPaperReload(true)
        );
    }

    @Override
    public void onEnable() {
        instance = this;
        scheduler = UniversalScheduler.getScheduler(this);
        CommandAPI.onEnable();
        reload();
        LibCommand.getInstance().register();
        loadManagers();
        registerListeners();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }

    public void reload() {
        MainConfig.getInstance().reload();
        MessageConfig.getInstance().reload();
        Bukkit.getPluginManager().callEvent(new DaisyLibReloadEvent());
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new BlockUtils(), this);
    }

    private void loadManagers() {
        if (!VaultManager.getInstance().load()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        RewardManager.getInstance().load();
    }

    public static DaisyLib getInstance() { return instance; }

    public static TaskScheduler getScheduler() { return scheduler; }

}

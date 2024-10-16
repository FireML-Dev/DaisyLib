package uk.firedev.daisylib.local;

import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.events.DaisyLibReloadEvent;
import uk.firedev.daisylib.local.command.LibCommand;
import uk.firedev.daisylib.local.config.ExampleConfig;
import uk.firedev.daisylib.local.config.MainConfig;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.daisylib.requirement.RequirementManager;
import uk.firedev.daisylib.reward.RewardManager;
import uk.firedev.daisylib.utils.BlockUtils;

public final class DaisyLib extends JavaPlugin {

    private static DaisyLib instance;
    private static TaskScheduler scheduler;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this)
                .shouldHookPaperReload(true)
                .usePluginNamespace()
        );
    }

    @Override
    public void onEnable() {
        instance = this;
        scheduler = UniversalScheduler.getScheduler(this);
        CommandAPI.onEnable();
        ExampleConfig.load();
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
        VaultManager.getInstance().load();
        RewardManager.getInstance().load();
        RequirementManager.getInstance().load();
    }

    public static DaisyLib getInstance() { return instance; }

    public static TaskScheduler getScheduler() { return scheduler; }

    public boolean isPluginEnabled(@NotNull String pluginName) {
        return getServer().getPluginManager().isPluginEnabled(pluginName);
    }

}

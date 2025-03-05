package uk.firedev.daisylib.local;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.events.CustomEventListener;
import uk.firedev.daisylib.events.DaisyLibReloadEvent;
import uk.firedev.daisylib.local.command.LibCommand;
import uk.firedev.daisylib.local.config.ExampleConfig;
import uk.firedev.daisylib.local.config.MainConfig;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.daisylib.requirement.RequirementManager;
import uk.firedev.daisylib.reward.RewardManager;

public final class DaisyLib extends JavaPlugin {

    private static DaisyLib instance;
    private Metrics metrics;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this)
                .shouldHookPaperReload(true)
                .usePluginNamespace()
                .missingExecutorImplementationMessage("You are not able to use this command!")
        );
    }

    @Override
    public void onEnable() {
        instance = this;
        CommandAPI.onEnable();
        ExampleConfig.load();
        reload();
        LibCommand.getCommand().register();
        getServer().getPluginManager().registerEvents(new CustomEventListener(), this);
        loadManagers();
        loadMetrics();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }

    public void reload() {
        MainConfig.getInstance().init();
        MessageConfig.getInstance().init();
        Bukkit.getPluginManager().callEvent(new DaisyLibReloadEvent());
    }

    private void loadMetrics() {
        this.metrics = new Metrics(this, 24173);
    }

    private void loadManagers() {
        VaultManager.getInstance().load();
        RewardManager.getInstance().load();
        RequirementManager.getInstance().load();
    }

    public static DaisyLib getInstance() { return instance; }

}

package uk.firedev.daisylib.local;

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

public final class DaisyLib extends JavaPlugin {

    private static DaisyLib instance;

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

    private void registerListeners() {}

    private void loadManagers() {
        VaultManager.getInstance().load();
        RewardManager.getInstance().load();
        RequirementManager.getInstance().load();
    }

    public static DaisyLib getInstance() { return instance; }
    
    public boolean isPluginEnabled(@NotNull String pluginName) {
        return getServer().getPluginManager().isPluginEnabled(pluginName);
    }

}

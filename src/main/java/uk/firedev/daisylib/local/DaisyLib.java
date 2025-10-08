package uk.firedev.daisylib.local;

import com.jeff_media.customblockdata.CustomBlockData;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPIPaperConfig;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.addons.requirements.ExpRequirementAddon;
import uk.firedev.daisylib.addons.requirements.HealthRequirementAddon;
import uk.firedev.daisylib.addons.requirements.HoldingRequirementAddon;
import uk.firedev.daisylib.addons.requirements.MoneyRequirementAddon;
import uk.firedev.daisylib.addons.requirements.PermissionRequirementAddon;
import uk.firedev.daisylib.addons.rewards.CommandRewardAddon;
import uk.firedev.daisylib.addons.rewards.ExpRewardAddon;
import uk.firedev.daisylib.addons.rewards.HealthRewardAddon;
import uk.firedev.daisylib.addons.rewards.ItemRewardAddon;
import uk.firedev.daisylib.addons.rewards.MoneyRewardAddon;
import uk.firedev.daisylib.addons.rewards.PermissionRewardAddon;
import uk.firedev.daisylib.events.CustomEventListener;
import uk.firedev.daisylib.events.DaisyLibReloadEvent;
import uk.firedev.daisylib.local.command.LibCommand;
import uk.firedev.daisylib.local.config.ExampleConfig;
import uk.firedev.daisylib.local.config.MainConfig;
import uk.firedev.daisylib.local.config.MessageConfig;

public final class DaisyLib extends JavaPlugin {

    private static DaisyLib instance;
    private Metrics metrics;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIPaperConfig(this)
                .missingExecutorImplementationMessage("You are not able to use this command!")
                .fallbackToLatestNMS(true)
        );
    }

    @Override
    public void onEnable() {
        instance = this;
        CommandAPI.onEnable();
        CustomBlockData.registerListener(this);
        ExampleConfig.load();
        reload();
        LibCommand.getCommand().register();
        getServer().getPluginManager().registerEvents(new CustomEventListener(), this);
        loadManagers();
        loadAddons();
        loadMetrics();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }

    public void reload() {
        MainConfig.getInstance().init();
        MessageConfig.getInstance().init();
        new DaisyLibReloadEvent().callEvent();
    }

    private void loadMetrics() {
        this.metrics = new Metrics(this, 24173);
    }

    private void loadManagers() {
        VaultManager.getInstance().load();
    }

    private void loadAddons() {
        // RewardAddons
        new CommandRewardAddon().register();
        new ExpRewardAddon().register();
        new HealthRewardAddon().register();
        new ItemRewardAddon().register();
        new MoneyRewardAddon().register();
        new PermissionRewardAddon().register();

        // RequirementAddons
        new ExpRequirementAddon().register();
        new HealthRequirementAddon().register();
        new HoldingRequirementAddon().register();
        new MoneyRequirementAddon().register();
        new PermissionRequirementAddon().register();
    }

    public static DaisyLib getInstance() { return instance; }

}

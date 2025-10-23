package uk.firedev.daisylib.local;

import com.jeff_media.customblockdata.CustomBlockData;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIPaperConfig;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.addons.requirement.types.ExpRequirementAddon;
import uk.firedev.daisylib.addons.requirement.types.HealthRequirementAddon;
import uk.firedev.daisylib.addons.requirement.types.HoldingRequirementAddon;
import uk.firedev.daisylib.addons.requirement.types.MoneyRequirementAddon;
import uk.firedev.daisylib.addons.requirement.types.PermissionRequirementAddon;
import uk.firedev.daisylib.addons.requirement.types.WorldRequirementAddon;
import uk.firedev.daisylib.addons.reward.types.CommandRewardAddon;
import uk.firedev.daisylib.addons.reward.types.ExpRewardAddon;
import uk.firedev.daisylib.addons.reward.types.HealthRewardAddon;
import uk.firedev.daisylib.addons.reward.types.ItemRewardAddon;
import uk.firedev.daisylib.addons.reward.types.MoneyRewardAddon;
import uk.firedev.daisylib.addons.reward.types.PermissionRewardAddon;
import uk.firedev.daisylib.events.CustomEventListener;
import uk.firedev.daisylib.events.DaisyLibReloadEvent;
import uk.firedev.daisylib.local.command.LibCommandBrigadier;
import uk.firedev.daisylib.local.config.ExampleConfig;
import uk.firedev.daisylib.local.config.MainConfig;
import uk.firedev.daisylib.local.config.MessageConfig;

public final class DaisyLib extends JavaPlugin {

    private static DaisyLib instance;
    private Metrics metrics;

    public DaisyLib() {
        if (instance != null) {
            throw new UnsupportedOperationException(getClass().getName() + " has already been assigned!");
        }
        instance = this;
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIPaperConfig(this)
                .missingExecutorImplementationMessage("You are not able to use this command!")
                .fallbackToLatestNMS(true)
        );
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();
        CustomBlockData.registerListener(this);
        ExampleConfig.load();
        reload();
        registerCommands();
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
        new WorldRequirementAddon().register();
    }

    public static @NotNull DaisyLib getInstance() {
        if (instance == null) {
            throw new UnsupportedOperationException(DaisyLib.class.getSimpleName() + " has not been assigned!");
        }
        return instance;
    }

    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            LibCommandBrigadier.register(commands.registrar());
        });
    }

}

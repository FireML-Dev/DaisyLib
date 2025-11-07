package uk.firedev.daisylib.local;

import com.jeff_media.customblockdata.CustomBlockData;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.addons.action.types.*;
import uk.firedev.daisylib.addons.requirement.types.*;
import uk.firedev.daisylib.addons.reward.types.*;
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
        registerCommands();
    }

    @Override
    public void onEnable() {
        CustomBlockData.registerListener(this);
        ExampleConfig.load();
        reload();
        getServer().getPluginManager().registerEvents(new CustomEventListener(), this);
        loadManagers();
        loadAddons();
        loadMetrics();
    }

    @Override
    public void onDisable() {}

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

        // ActionAddons
        new BreakActionAddon().register();
        new BreedActionAddon().register();
        new BrushActionAddon().register();
        new BucketActionAddon().register();
        new ConsumeActionAddon().register();
        new DyeActionAddon().register();
        new EnchantActionAddon().register();
        new FishActionAddon().register();
        new KillActionAddon().register();
        new MilkActionAddon().register();
        new PlaceActionAddon().register();
        new ShearBlockActionAddon().register();
        new ShearEntityActionAddon().register();
        new SmeltActionAddon().register();
        new TameActionAddon().register();
        new TradeActionAddon().register();
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

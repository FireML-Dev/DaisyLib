package uk.firedev.daisylib.local;

import com.jeff_media.customblockdata.CustomBlockData;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.VaultManager;
import uk.firedev.daisylib.addons.action.types.BreakActionAddon;
import uk.firedev.daisylib.addons.action.types.BreedActionAddon;
import uk.firedev.daisylib.addons.action.types.BrushActionAddon;
import uk.firedev.daisylib.addons.action.types.BucketEntityActionAddon;
import uk.firedev.daisylib.addons.action.types.ConsumeActionAddon;
import uk.firedev.daisylib.addons.action.types.DyeActionAddon;
import uk.firedev.daisylib.addons.action.types.EnchantActionAddon;
import uk.firedev.daisylib.addons.action.types.FishActionAddon;
import uk.firedev.daisylib.addons.action.types.KillActionAddon;
import uk.firedev.daisylib.addons.action.types.MilkActionAddon;
import uk.firedev.daisylib.addons.action.types.PlaceActionAddon;
import uk.firedev.daisylib.addons.action.types.ShearBlockActionAddon;
import uk.firedev.daisylib.addons.action.types.ShearEntityActionAddon;
import uk.firedev.daisylib.addons.action.types.SmeltActionAddon;
import uk.firedev.daisylib.addons.action.types.TameActionAddon;
import uk.firedev.daisylib.addons.action.types.TradeActionAddon;
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
import uk.firedev.daisylib.local.command.MainCommand;
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
        MainConfig.getInstance().pullDefaults();
        MessageConfig.getInstance().pullDefaults();
        getServer().getPluginManager().registerEvents(new CustomEventListener(), this);
        loadManagers();
        loadAddons();
        loadMetrics();
    }

    @Override
    public void onDisable() {}

    public void reload() {
        ExampleConfig.load();
        MainConfig.getInstance().reload();
        MessageConfig.getInstance().reload();
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
        new BucketEntityActionAddon().register();
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
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands ->
            commands.registrar().register(MainCommand.get())
        );
    }

}

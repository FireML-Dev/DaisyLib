package uk.firedev.daisylib.common;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.common.addons.requirement.defaults.ExpRequirementAddon;
import uk.firedev.daisylib.common.addons.requirement.defaults.HealthRequirementAddon;
import uk.firedev.daisylib.common.addons.requirement.defaults.HoldingRequirementAddon;
import uk.firedev.daisylib.common.addons.requirement.defaults.MoneyRequirementAddon;
import uk.firedev.daisylib.common.addons.requirement.defaults.PermissionRequirementAddon;
import uk.firedev.daisylib.common.addons.requirement.defaults.WorldRequirementAddon;
import uk.firedev.daisylib.common.addons.reward.defaults.CommandRewardAddon;
import uk.firedev.daisylib.common.addons.reward.defaults.HealthRewardAddon;
import uk.firedev.daisylib.common.addons.reward.defaults.ItemRewardAddon;
import uk.firedev.daisylib.common.addons.reward.defaults.MoneyRewardAddon;
import uk.firedev.daisylib.common.events.CustomEventListener;
import uk.firedev.daisylib.common.external.vault.VaultWrapper;
import uk.firedev.daisylib.common.logging.Logging;
import uk.firedev.daisylib.common.utils.CommonUtils;

import java.util.function.Supplier;

public class DaisyLib {

    private static final DaisyLib INSTANCE = new DaisyLib();
    public static final boolean IS_FOLIA = CommonUtils.classExists("io.papermc.paper.threadedregions.RegionizedServer");

    private JavaPlugin plugin;
    private Logging logging = Logging.logging("DaisyLib");

    private DaisyLib() {}

    public static @NonNull DaisyLib get() {
        return INSTANCE;
    }

    public void init(@NonNull JavaPlugin plugin) {
        if (this.plugin != null) {
            throw new UnsupportedOperationException("DaisyLib is already initialized!");
        }
        this.plugin = plugin;
        this.logging = Logging.logging("DaisyLib via " + plugin.getName());

        VaultWrapper.get().load();
        registerListeners();
    }

    public @NonNull JavaPlugin getPlugin() {
        if (this.plugin == null) {
            logging.error("DaisyLib has not been initialized. You must call DaisyLib#init(JavaPlugin).");
            throw new IllegalStateException();
        }
        return this.plugin;
    }

    public @NonNull Logging getLogging() {
        return this.logging;
    }

    private void registerListeners() {
        JavaPlugin plugin = getPlugin();
        PluginManager pm = plugin.getServer().getPluginManager();

        pm.registerEvents(new CustomEventListener(), plugin);
    }

    public void loadDefaultAddons() {
        // Item
        // Requirement
        new ExpRequirementAddon().register();
        new HealthRequirementAddon().register();
        new HoldingRequirementAddon().register();
        new MoneyRequirementAddon().register();
        new PermissionRequirementAddon().register();
        new WorldRequirementAddon().register();
        // Reward
        new CommandRewardAddon().register();
        new ExpRequirementAddon().register();
        new HealthRewardAddon().register();
        new ItemRewardAddon().register();
        new MoneyRewardAddon().register();
        new PermissionRequirementAddon().register();
    }

    public static class Settings {

        // Should debug messages be shown? Defaults to false.
        public static @NonNull Supplier<@NonNull Boolean> ENABLE_DEBUG = () -> false;

        // Should Minecraft messages support legacy characters? Defaults to false.
        public static @NonNull Supplier<@NonNull Boolean> ALLOW_LEGACY_MESSAGES = () -> false;

    }

}

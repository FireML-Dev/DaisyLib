package uk.firedev.daisylib.internal;

import com.jeff_media.customblockdata.CustomBlockData;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.event.DaisyLibReloadEvent;
import uk.firedev.daisylib.internal.command.MainCommand;
import uk.firedev.daisylib.internal.config.MainConfig;
import uk.firedev.daisylib.internal.config.MessageConfig;

public class DaisyLibPlugin extends JavaPlugin {

    private static DaisyLibPlugin INSTANCE;

    private Metrics metrics;

    public DaisyLibPlugin() {
        if (INSTANCE != null) {
            throw new UnsupportedOperationException(getClass().getName() + " has already been assigned!");
        }
        INSTANCE = this;
    }

    public static @NotNull DaisyLibPlugin getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException(DaisyLibPlugin.class.getSimpleName() + " has not been assigned!");
        }
        return INSTANCE;
    }

    @Override
    public void onLoad() {
        registerCommands();
    }

    @Override
    public void onEnable() {
        CustomBlockData.registerListener(this);
        loadConfigs();
        // TODO load Addons
        this.metrics = new Metrics(this, 24173);
    }

    public void reload() {
        reloadConfigs();
        new DaisyLibReloadEvent().callEvent();
    }

    private void loadConfigs() {
        MainConfig.getInstance().init();
        MessageConfig.getInstance().init();
    }

    private void reloadConfigs() {
        MainConfig.getInstance().reload();
        MessageConfig.getInstance().reload();
    }

    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands ->
            commands.registrar().register(MainCommand.get())
        );
    }

}

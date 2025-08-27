package uk.firedev.daisylib.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WarmupHandler extends BukkitRunnable {

    // Class things
    private final int warmupSeconds;
    private final Player player;
    private Consumer<Player> completionAction = null;
    private BiConsumer<Integer, Player> waitAction = null;
    private String bypassPermission = null;

    // Task things
    private BukkitTask task = null;
    private int passed = 0;

    private WarmupHandler(int warmupSeconds, @NotNull Player player) {
        this.warmupSeconds = warmupSeconds;
        this.player = player;
    }

    public static WarmupHandler create(int warmupSeconds, @NotNull Player player) {
        return new WarmupHandler(warmupSeconds, player);
    }

    /**
     * Fired when the warmup is complete
     */
    public WarmupHandler withCompletionAction(@NotNull Consumer<Player> completionAction) {
        this.completionAction = completionAction;
        return this;
    }

    /**
     * Fired for every second of waiting
     */
    public WarmupHandler withWaitAction(@NotNull BiConsumer<Integer, Player> waitAction) {
        this.waitAction = waitAction;
        return this;
    }

    /**
     * The permission to bypass the warmup
     */
    public WarmupHandler withBypassPermission(@NotNull String bypassPermission) {
        this.bypassPermission = bypassPermission;
        return this;
    }

    public void start(@NotNull Plugin plugin) {
        if (task != null) {
            return;
        }
        if (warmupSeconds == passed) {
            throw new IllegalStateException("Attempted to start a warmup that has already been executed!");
        }
        if (bypassPermission != null && player.hasPermission(bypassPermission)) {
            completionAction.accept(player);
            passed = warmupSeconds;
            return;
        }
        this.task = runTaskTimer(plugin, 0L, 20L);
    }

    public void stop() {
        if (task == null) {
            return;
        }
        task.cancel();
        task = null;
    }

    @Override
    public void run() {
        if (warmupSeconds == passed) {
            // Warmup is complete
            if (completionAction != null) {
                completionAction.accept(player);
            }
            stop();
            return;
        }
        passed++;
        if (waitAction != null) {
            waitAction.accept(passed, player);
        }
    }

}

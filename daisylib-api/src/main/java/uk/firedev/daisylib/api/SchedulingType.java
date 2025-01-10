package uk.firedev.daisylib.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public enum SchedulingType {
    ASYNC,
    SYNC,
    NONE;

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();

    /**
     * Runs a task based on the type.
     * @param runnable the task to be executed
     * @param plugin the plugin to run the task
     */
    public void run(@NotNull Runnable runnable, @NotNull Plugin plugin) {
        switch (this) {
            case NONE -> runnable.run();
            case ASYNC -> scheduler.runTaskAsynchronously(plugin, runnable);
            case SYNC -> scheduler.runTask(plugin, runnable);
        }
    }

    /**
     * Runs a delayed task based on the type.
     * If the type is {@link SchedulingType#NONE}, the delay will be ignored.
     *
     * @param runnable the task to be executed
     * @param plugin the plugin to run the task
     * @param tickDelay the delay in ticks
     */
    public void runDelayed(@NotNull Runnable runnable, @NotNull Plugin plugin, long tickDelay) {
        switch (this) {
            case NONE -> runnable.run();
            case SYNC -> scheduler.runTaskLater(plugin, runnable, tickDelay);
            case ASYNC -> scheduler.runTaskLaterAsynchronously(plugin, runnable, tickDelay);
        }
    }

}

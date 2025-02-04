package uk.firedev.daisylib.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

/**
 * A simple way to schedule tasks using the {@link BukkitScheduler}.
 */
public enum SchedulingType {
    ASYNC,
    SYNC,
    NONE;

    /**
     * Runs a task based on the type.
     * @param runnable the task to be executed
     * @param plugin the plugin to run the task
     */
    public void run(@NotNull Runnable runnable, @NotNull Plugin plugin) {
        switch (this) {
            case NONE -> runnable.run();
            case ASYNC -> Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
            case SYNC -> Bukkit.getScheduler().runTask(plugin, runnable);
        }
    }

    /**
     * Runs a delayed task based on the type.
     * <p>
     * If the type is {@link SchedulingType#NONE}, the delay will be ignored.
     *
     * @param runnable the task to be executed
     * @param plugin the plugin to run the task
     * @param tickDelay the delay in ticks
     */
    public void runDelayed(@NotNull Runnable runnable, @NotNull Plugin plugin, long tickDelay) {
        switch (this) {
            case NONE -> runnable.run();
            case SYNC -> Bukkit.getScheduler().runTaskLater(plugin, runnable, tickDelay);
            case ASYNC -> Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, tickDelay);
        }
    }

}

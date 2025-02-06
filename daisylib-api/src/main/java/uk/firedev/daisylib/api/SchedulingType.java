package uk.firedev.daisylib.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

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

    /**
     * Runs the provided task in a {@link CompletableFuture} instead of {@link BukkitScheduler}.
     *
     * @param runnable the task to be executed.
     */
    public CompletableFuture<Void> runFuture(@NotNull Runnable runnable) {
        return switch (this) {
            case NONE, SYNC -> {
                runnable.run();
                yield CompletableFuture.completedFuture(null);
            }
            case ASYNC -> CompletableFuture.runAsync(runnable);
        };
    }

    /**
     * Runs the provided supplier in a {@link CompletableFuture}.
     *
     * @param supplier the supplier to be executed.
     */
    public <T> CompletableFuture<T> supplyFuture(@NotNull Supplier<T> supplier) {
        return switch (this) {
            case NONE, SYNC -> CompletableFuture.completedFuture(supplier.get());
            case ASYNC -> CompletableFuture.supplyAsync(supplier);
        };
    }

}

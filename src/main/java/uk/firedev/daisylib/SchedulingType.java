package uk.firedev.daisylib;

import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import org.jetbrains.annotations.NotNull;

public enum SchedulingType {
    ASYNC,
    SYNC,
    NONE;

    /**
     * Runs a task based on the type.
     *
     * @param runnable the task to be executed
     * @param scheduler the task scheduler to use
     */
    public void run(@NotNull Runnable runnable, @NotNull TaskScheduler scheduler) {
        switch (this) {
            case NONE -> runnable.run();
            case ASYNC -> scheduler.runTaskAsynchronously(runnable);
            case SYNC -> scheduler.runTask(runnable);
        }
    }

    /**
     * Runs a delayed task based on the type.
     * If the type is {@link SchedulingType#NONE}, the delay will be ignored.
     *
     * @param runnable the task to be executed
     * @param scheduler the task scheduler to use
     */
    public void runDelayed(@NotNull Runnable runnable, @NotNull TaskScheduler scheduler, long tickDelay) {
        switch (this) {
            case NONE -> runnable.run();
            case SYNC -> scheduler.runTaskLater(runnable, tickDelay);
            case ASYNC -> scheduler.runTaskLaterAsynchronously(runnable, tickDelay);
        }
    }

}

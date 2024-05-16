package uk.firedev.daisylib;

import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import org.jetbrains.annotations.NotNull;

public class Scheduling {

    /**
     * Runs a task based on the specified scheduling type.
     *
     * @param runnable the task to be executed
     * @param scheduler the task scheduler to use for ASYNC and SYNC types
     * @param type the scheduling type
     */
    public static void run(@NotNull Runnable runnable, @NotNull TaskScheduler scheduler, @NotNull SchedulingType type) {
        switch (type) {
            case NONE -> runnable.run();
            case ASYNC -> scheduler.runTaskAsynchronously(runnable);
            case SYNC -> scheduler.runTask(runnable);
        }
    }

    /**
     * The type of scheduling to use for task execution.
     */
    public enum SchedulingType {
        ASYNC,
        SYNC,
        NONE
    }

}

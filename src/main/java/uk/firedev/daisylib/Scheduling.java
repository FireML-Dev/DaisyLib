package uk.firedev.daisylib;

import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

/**
 * @deprecated This class will be removed for 2.1.0-SNAPSHOT. Use {@link uk.firedev.daisylib.SchedulingType} instead.
 */
@Deprecated(forRemoval = true)
public class Scheduling {

    /**
     * Runs a task based on the specified scheduling type.
     *
     * @deprecated This will be removed for 2.1.0-SNAPSHOT. Use {@link uk.firedev.daisylib.SchedulingType#run(Runnable, TaskScheduler)} instead.
     * @param runnable the task to be executed
     * @param scheduler the task scheduler to use for ASYNC and SYNC types
     * @param type the scheduling type
     */
    @Deprecated(forRemoval = true)
    public static void run(@NotNull Runnable runnable, @NotNull TaskScheduler scheduler, @NotNull SchedulingType type) {
        type.run(runnable, scheduler);
    }

    /**
     * @deprecated Use {@link uk.firedev.daisylib.SchedulingType} instead.
     */
    @Deprecated(forRemoval = true)
    public enum SchedulingType {
        ASYNC,
        SYNC,
        NONE;

        /**
         * Runs a task based on the type.
         *
         * @param runnable the task to be executed
         * @param scheduler the task scheduler to use for ASYNC and SYNC types
         */
        public void run(@NotNull Runnable runnable, @NotNull TaskScheduler scheduler) {
            switch (this) {
                case NONE -> runnable.run();
                case ASYNC -> scheduler.runTaskAsynchronously(runnable);
                case SYNC -> scheduler.runTask(runnable);
            }
        }
    }

}


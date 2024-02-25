package uk.firedev.daisylib;

import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;

public class Scheduling {

    public static void run(Runnable runnable, TaskScheduler scheduler, SchedulingType type) {
        switch (type) {
            case NONE -> runnable.run();
            case ASYNC -> scheduler.runTaskAsynchronously(runnable);
            case SYNC -> scheduler.runTask(runnable);
        }
    }

    public enum SchedulingType {
        ASYNC,
        SYNC,
        NONE
    }

}

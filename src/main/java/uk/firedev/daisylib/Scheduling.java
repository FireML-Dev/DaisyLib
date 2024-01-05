package uk.firedev.daisylib;

import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;

public class Scheduling {

    public static void run(Runnable runnable, TaskScheduler scheduler, Boolean async) {
        if (async == null) {
            runnable.run();
            return;
        }
        if (async) {
            scheduler.runTaskAsynchronously(runnable);
        } else {
            scheduler.runTask(runnable);
        }
    }

}

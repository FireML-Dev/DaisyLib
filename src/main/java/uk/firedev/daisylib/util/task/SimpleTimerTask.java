package uk.firedev.daisylib.util.task;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public abstract class SimpleTimerTask extends TimerTask {

    private final Timer timer = new Timer();
    private final TimeUnit unit;
    private final long interval;

    public SimpleTimerTask(@NotNull TimeUnit unit, long interval) {
        this.unit = unit;
        this.interval = interval;
    }

    public void start() {
        timer.scheduleAtFixedRate(
            this,
            0,
            unit.toMillis(interval)
        );
    }

    public void stop() {
        cancel();
        timer.cancel();
    }

    public Date getNextRun(int hour, int minute, TimeZone timeZone) {
        Calendar next = Calendar.getInstance();
        if (timeZone == null) {
            next.setTimeZone(TimeZone.getDefault());
        } else {
            next.setTimeZone(timeZone);
        }
        if (next.get(Calendar.HOUR_OF_DAY) >= hour) {
            next.set(Calendar.DAY_OF_YEAR, next.get(Calendar.DAY_OF_YEAR) + 1);
        }
        next.set(Calendar.HOUR_OF_DAY, hour);
        next.set(Calendar.MINUTE, minute);
        next.set(Calendar.SECOND, 0);
        next.set(Calendar.MILLISECOND, 1);
        return next.getTime();
    }

}

package uk.firedev.daisylib.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class TimerScheduling {

    public Timer timer = new Timer();
    public boolean started = false;

    private int hour;
    private int minute;
    private TimeZone timeZone;

    public TimerScheduling(int hour, int minute, @Nullable TimeZone timeZone) {
        this.hour = hour;
        this.minute = minute;
        this.timeZone = timeZone;
    }

    public void start(@NotNull TimerTask task) {
        if (started) {
            return;
        }
        Date nextDate = getNextRun(hour, minute, timeZone);

        timer.schedule(task, nextDate);
    }

    public void stop() {
        if (!started) {
            return;
        }
        timer.cancel();
        timer = new Timer();
        started = false;
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

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    
}

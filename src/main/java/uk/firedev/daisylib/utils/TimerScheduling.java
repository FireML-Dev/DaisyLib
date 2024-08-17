package uk.firedev.daisylib.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
        timer = new Timer();
        started = false;
    }

    public Date getNextRun(int hour, int minute, TimeZone timeZone) {
        Calendar nextUpkeep = Calendar.getInstance();
        if (timeZone == null) {
            nextUpkeep.setTimeZone(TimeZone.getDefault());
        } else {
            nextUpkeep.setTimeZone(timeZone);
        }
        if (nextUpkeep.get(Calendar.HOUR_OF_DAY) >= hour) {
            nextUpkeep.set(Calendar.DAY_OF_YEAR, nextUpkeep.get(Calendar.DAY_OF_YEAR) + 1);
        }
        nextUpkeep.set(Calendar.HOUR_OF_DAY, hour);
        nextUpkeep.set(Calendar.MINUTE, minute);
        nextUpkeep.set(Calendar.SECOND, 0);
        nextUpkeep.set(Calendar.MILLISECOND, 1);
        return nextUpkeep.getTime();
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

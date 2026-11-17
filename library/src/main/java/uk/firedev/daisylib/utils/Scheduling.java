package uk.firedev.daisylib.utils;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;

import java.util.function.Consumer;

public class Scheduling {

    /**
     * @see io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler#run(Plugin, Consumer)
     */
    public static @NonNull ScheduledTask runTask(@NonNull Runnable runnable) {
        return Bukkit.getGlobalRegionScheduler().run(
            DaisyLib.get().getPlugin(),
            task -> runnable.run()
        );
    }

    /**
     * @see io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler#runDelayed(Plugin, Consumer, long)
     */
    public static @NonNull ScheduledTask runTaskLater(@NonNull Runnable runnable, long delayTicks) {
        return Bukkit.getGlobalRegionScheduler().runDelayed(
            DaisyLib.get().getPlugin(),
            task -> runnable.run(),
            calculateDelay(delayTicks)
        );
    }

    /**
     * @see io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler#runAtFixedRate(Plugin, Consumer, long, long)
     */
    public static @NonNull ScheduledTask runTaskTimer(@NonNull Runnable runnable, long delayTicks, long periodTicks) {
        return Bukkit.getGlobalRegionScheduler().runAtFixedRate(
            DaisyLib.get().getPlugin(),
            task -> runnable.run(),
            calculateDelay(delayTicks),
            periodTicks
        );
    }

    /**
     * @see io.papermc.paper.threadedregions.scheduler.RegionScheduler#run(Plugin, Location, Consumer)
     */
    public static @NonNull ScheduledTask runTask(@NonNull Location location, @NonNull Runnable runnable) {
        return Bukkit.getRegionScheduler().run(
            DaisyLib.get().getPlugin(),
            location,
            task -> runnable.run()
        );
    }

    /**
     * @see io.papermc.paper.threadedregions.scheduler.RegionScheduler#runDelayed(Plugin, Location, Consumer, long)
     */
    public static @NonNull ScheduledTask runTaskLater(@NonNull Location location, @NonNull Runnable runnable, long delayTicks) {
        return Bukkit.getRegionScheduler().runDelayed(
            DaisyLib.get().getPlugin(),
            location,
            task -> runnable.run(),
            calculateDelay(delayTicks)
        );
    }

    /**
     * @see io.papermc.paper.threadedregions.scheduler.RegionScheduler#runAtFixedRate(Plugin, Location, Consumer, long, long)
     */
    public static @NonNull ScheduledTask runTaskTimer(@NonNull Location location, @NonNull Runnable runnable, long delayTicks, long periodTicks) {
        return Bukkit.getRegionScheduler().runAtFixedRate(
            DaisyLib.get().getPlugin(),
            location,
            task -> runnable.run(),
            calculateDelay(delayTicks),
            periodTicks
        );
    }

    /**
     * @see io.papermc.paper.threadedregions.scheduler.EntityScheduler#run(Plugin, Consumer, Runnable)
     */
    public @Nullable ScheduledTask runTask(@NonNull Entity entity, @NonNull Runnable runnable) {
        return entity.getScheduler().run(
            DaisyLib.get().getPlugin(),
            task -> runnable.run(),
            null
        );
    }

    /**
     * @see io.papermc.paper.threadedregions.scheduler.EntityScheduler#runDelayed(Plugin, Consumer, Runnable, long)
     */
    public @Nullable ScheduledTask runTaskLater(@NonNull Entity entity, @NonNull Runnable runnable, long delayTicks) {
        return entity.getScheduler().runDelayed(
            DaisyLib.get().getPlugin(),
            task -> runnable.run(),
            null,
            calculateDelay(delayTicks)
        );
    }

    /**
     * @see io.papermc.paper.threadedregions.scheduler.EntityScheduler#runAtFixedRate(Plugin, Consumer, Runnable, long, long)
     */
    public @Nullable ScheduledTask runTaskTimer(@NonNull Entity entity, @NonNull Runnable runnable, long delayTicks, long periodTicks) {
        return entity.getScheduler().runAtFixedRate(
            DaisyLib.get().getPlugin(),
            task -> runnable.run(),
            null,
            calculateDelay(delayTicks),
            periodTicks
        );
    }

    private static long calculateDelay(long initialDelay) {
        // Folia requires the initial delay to be 1 or above.
        if (DaisyLib.IS_FOLIA) {
            return Math.max(1, initialDelay);
        }
        return initialDelay;
    }

}

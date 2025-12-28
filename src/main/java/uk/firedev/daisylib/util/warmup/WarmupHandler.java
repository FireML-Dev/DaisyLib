package uk.firedev.daisylib.util.warmup;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WarmupHandler extends BukkitRunnable {

    // Class things
    private final Plugin plugin;
    private final int warmupSeconds;
    private final Player player;
    private final Location initialLocation;

    private Consumer<Player> completionAction = null;
    private BiConsumer<Integer, Player> waitAction = null;
    private Consumer<Player> movementAction = null;
    private String bypassPermission = null;
    private boolean allowMovement = true;

    // Task things
    private BukkitTask task = null;
    private int passed = 0;

    protected WarmupHandler(int warmupSeconds, @NotNull Player player, @NotNull Plugin plugin) {
        this.plugin = plugin;
        this.warmupSeconds = warmupSeconds;
        this.player = player;
        this.initialLocation = processLocation(player.getLocation());
    }

    public static WarmupHandler warmupHandler(int warmupSeconds, @NotNull Player player, @NotNull Plugin plugin) {
        return new WarmupHandler(warmupSeconds, player, plugin);
    }

    /**
     * Controls whether movement should be allowed during warmup.
     * <p>
     * Defaults to true.
     */
    public WarmupHandler allowMovement(boolean allowMovement) {
        this.allowMovement = allowMovement;
        return this;
    }

    /**
     * Fired when the warmup is complete
     */
    public WarmupHandler withCompletionAction(@NotNull Consumer<Player> completionAction) {
        this.completionAction = completionAction;
        return this;
    }

    /**
     * Fired for every second of waiting
     */
    public WarmupHandler withWaitAction(@NotNull BiConsumer<Integer, Player> waitAction) {
        this.waitAction = waitAction;
        return this;
    }

    /**
     * Fired if {@link WarmupHandler#allowMovement} is false, and the player has moved.
     */
    public WarmupHandler withMovementAction(@NotNull Consumer<Player> movementAction) {
        this.movementAction = movementAction;
        return this;
    }

    /**
     * The permission to bypass the warmup
     */
    public WarmupHandler withBypassPermission(@NotNull String bypassPermission) {
        this.bypassPermission = bypassPermission;
        return this;
    }

    public void start() {
        if (task != null) {
            return;
        }
        if (warmupSeconds == passed) {
            throw new IllegalStateException("Attempted to start a warmup that has already been executed!");
        }
        if (bypassPermission != null && player.hasPermission(bypassPermission)) {
            if (completionAction != null) {
                completionAction.accept(player);
            }
            passed = warmupSeconds;
            return;
        }
        this.task = runTaskTimer(plugin, 0L, 20L);
    }

    public void stop() {
        if (task == null) {
            return;
        }
        task.cancel();
        task = null;
    }

    @Override
    public void run() {
        // Stop if the player logs out at any point
        if (!player.isConnected()) {
            stop();
            return;
        }
        // Check for movement if movement is not allowed.
        Location newLocation = processLocation(player.getLocation());
        if (!allowMovement && !initialLocation.equals(newLocation)) {
            if (movementAction != null) {
                movementAction.accept(player);
            }
            stop();
            return;
        }
        // Check if the time has passed.
        if (warmupSeconds == passed) {
            // Warmup is complete
            if (completionAction != null) {
                completionAction.accept(player);
            }
            stop();
            return;
        }
        // Handle waiting
        int remainingTime = (warmupSeconds - passed);
        passed++;
        if (waitAction != null) {
            waitAction.accept(remainingTime, player);
        }
    }

    /**
     * Processes a location into a non-bs location for the {@link #allowMovement} variable.
     * <p>
     * Centers the location and removes rotation.
     */
    private @NotNull Location processLocation(@NotNull Location location) {
        return location.toCenterLocation().setRotation(0, 0);
    }

}

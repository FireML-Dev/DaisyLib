package uk.firedev.daisylib.database;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.database.exceptions.DatabaseLoadException;
import uk.firedev.daisylib.util.Loggers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Shared values for all database implementations
 */
public abstract class Database {

    private final Plugin plugin;
    private Connection connection = null;
    private BukkitTask autoSave = null;

    private final List<DatabaseModule> modules = new ArrayList<>();

    public Database(@NonNull Plugin plugin) {
        this.plugin = plugin;
    }

    // Methods

    public void load() throws DatabaseLoadException {
        initConnection();
        initTables();
        startAutoSaveTask();
    }

    public void reload() {
        if (autoSave != null && !autoSave.isCancelled()) {
            stopAutoSaveTask();
            startAutoSaveTask();
        }
    }

    public void unload() {
        stopAutoSaveTask();
        saveAll();
        closeConnection();
    }

    /**
     * Saves this database and all modules.
     */
    public void saveAll() {
        save();
        modules.forEach(DatabaseModule::save);
    }

    public @NonNull Connection getConnection() {
        return Objects.requireNonNull(this.connection, "Connection was not initialized?");
    }

    public void setConnection(@NonNull Connection connection) throws IllegalArgumentException {
        Preconditions.checkArgument(this.connection == null, "Connection is already initialized!");
        this.connection = connection;
    }

    public void closeConnection() {
        if (this.connection == null) {
            return;
        }
        try {
            this.connection.close();
        } catch (SQLException ex) {
            Loggers.error(plugin.getComponentLogger(), "Failed to close database connection.");
        }
        this.connection = null;
    }

    public @NonNull Plugin getPlugin() {
        return this.plugin;
    }

    public void initTables() throws DatabaseLoadException {
        try {
            addTable(getTable(), getColumns());
            for (Map.Entry<String, String> entry : getColumns().entrySet()) {
                addColumn(getTable(), entry.getKey(), entry.getValue());
            }
        } catch (SQLException exception) {
            throw new DatabaseLoadException(exception);
        }
    }

    public void registerModule(@NonNull DatabaseModule module) {
        if (modules.contains(module)) {
            return;
        }
        module.init();
        modules.add(module);
    }

    public List<DatabaseModule> getLoadedModules() {
        return List.copyOf(modules);
    }

    public void startAutoSaveTask() {
        // Task is already running
        if (autoSave != null && !autoSave.isCancelled()) {
            return;
        }
        long tickInterval = getAutoSaveSeconds() * 20L;
        autoSave = Bukkit.getScheduler().runTaskTimerAsynchronously(
            plugin,
            this::saveAll,
            tickInterval,
            tickInterval
        );
    }

    public void stopAutoSaveTask() {
        if (autoSave == null || autoSave.isCancelled()) {
            return;
        }
        autoSave.cancel();
        autoSave = null;
    }

    // Abstract Methods

    /**
     * Saves this database
     */
    public abstract void save();

    public abstract @NonNull String getTable();

    public abstract @NonNull Map<String, String> getColumns();

    public abstract long getAutoSaveSeconds();

    public abstract void initConnection() throws DatabaseLoadException;

    public abstract boolean addTable(@NonNull String table, @NonNull Map<String, String> columns) throws SQLException;

    public abstract boolean addColumn(@NonNull String table, @NonNull String column, @NonNull String type) throws SQLException;

}

package uk.firedev.daisylib.api.database;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.utils.FileUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Helps with connecting to a SQLite database.
 */
public abstract class SQLiteDatabase {

    private Plugin plugin = null;
    private Connection connection = null;
    private final List<DatabaseModule> loadedModules = new ArrayList<>();
    private final String fileName;

    /**
     * Creates a new SQLiteDatabase instance with the specified file name.
     * @param plugin The plugin this database belongs to.
     * @param fileName The database's file name.
     */
    public SQLiteDatabase(@NotNull Plugin plugin, @NotNull String fileName) {
        this.fileName = fileName;
        setup(plugin);
    }

    /**
     * Creates a new SQLiteDatabase instance.
     * @param plugin The plugin this database belongs to.
     */
    public SQLiteDatabase(@NotNull Plugin plugin) {
        this.fileName = "data.db";
        setup(plugin);
    }

    private void setup(Plugin plugin) {
        this.plugin = plugin;
        initConnection();
    }

    public @NotNull Connection getConnection() {
        return Objects.requireNonNull(this.connection, "Connection was not initialized?");
    }

    public abstract void startAutoSaveTask();

    public abstract void stopAutoSaveTask();

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException ex) {
                Loggers.error(plugin.getComponentLogger(), "Failed to close database connection.");
            }
            this.connection = null;
        }
    }

    public boolean addTable(@NotNull String table, @NotNull Map<String, String> columns) {
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS " + table + " (");
        columns.forEach((column, type) -> builder.append(column).append(" ").append(type).append(", "));
        // To remove the trailing comma and space
        builder.setLength(builder.length() - 2);
        builder.append(")");
        try (Statement statement = this.connection.createStatement()) {
            statement.execute(builder.toString());
            return true;
        } catch (SQLException exception) {
            Loggers.error(plugin.getComponentLogger(), "Failed to create table " + table, exception);
            return false;
        }
    }

    public boolean addColumn(@NotNull String table, @NotNull String column, @NotNull String type) {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute("ALTER TABLE " + table + " ADD COLUMN " + column + " " + type);
            return true;
        } catch (SQLException exception) {
            if (exception.getMessage().contains("duplicate column name")) {
                return true;
            }
            Loggers.error(plugin.getComponentLogger(), "Failed to add column " + column + " to table " + table, exception);
            return false;
        }
    }

    public void registerModule(@NotNull DatabaseModule module) {
        if (this.connection == null) {
            throw new RuntimeException("Database connection is not initialized. Please call setup(Plugin) first.");
        }
        if (loadedModules.contains(module)) {
            return;
        }
        module.init();
        loadedModules.add(module);
    }

    public List<DatabaseModule> getLoadedModules() {
        if (this.connection == null) {
            throw new RuntimeException("Database connection is not initialized. Please call setup() first.");
        }
        return loadedModules;
    }

    public Plugin getPlugin() {
        if (this.connection == null) {
            throw new RuntimeException("Plugin is not available. Please call setup(Plugin) first.");
        }
        return plugin;
    }

    private void initConnection() {

        // Make sure the data folder exists
        if (!FileUtils.createDirectory(this.plugin.getDataFolder())) {
            Loggers.error(this.plugin.getComponentLogger(), "Failed to create the plugin's data folder!");
            return;
        }

        // Try to connect to the SQLite database
        String url = "jdbc:sqlite:" + this.plugin.getDataFolder() + "/" + this.fileName;

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException exception) {
            Loggers.error(plugin.getComponentLogger(), "Failed to connect to the database. Disabling " + plugin.getName() + ".", exception);
            Bukkit.getPluginManager().disablePlugin(plugin);
        }

        Loggers.info(plugin.getComponentLogger(), "Successfully connected to the database.");

    }

}

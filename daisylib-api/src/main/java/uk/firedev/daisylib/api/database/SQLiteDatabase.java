package uk.firedev.daisylib.api.database;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.api.utils.FileUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Creates a new SQLiteDatabase instance with the specified file name.
     * <p>
     * You must run {@link #setup(Plugin)} before using this instance.
     * @param fileName The database's file name.
     */
    public SQLiteDatabase(@NotNull String fileName) {
        this.fileName = fileName;
    }

    /**
     * Creates a new SQLiteDatabase instance.
     * <p>
     * You must run {@link #setup(Plugin)} before using this instance.
     */
    public SQLiteDatabase() {
        this.fileName = "data.db";
    }

    public void setup(Plugin plugin) {
        this.plugin = plugin;
        initConnection();
    }

    public Connection getConnection() {
        if (this.connection == null) {
            throw new RuntimeException("Database connection is not initialized. Please call setup(Plugin) first.");
        }
        return this.connection;
    }

    public abstract void startAutoSaveTask();

    public abstract void stopAutoSaveTask();

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                this.connection = null;
            } catch (SQLException ex) {
                Loggers.error(plugin.getComponentLogger(), "Failed to close database connection.");
            }
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
        } catch (SQLException | ClassNotFoundException e) {
            Loggers.error(plugin.getComponentLogger(), "Failed to connect to the database. Disabling " + plugin.getName() + ".");
            Loggers.logException(plugin.getComponentLogger(), e);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }

        Loggers.info(plugin.getComponentLogger(), "Successfully connected to the database.");

    }

}

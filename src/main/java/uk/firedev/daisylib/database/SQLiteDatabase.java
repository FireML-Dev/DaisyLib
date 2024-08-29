package uk.firedev.daisylib.database;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.utils.FileUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helps with connecting to a SQLite Database
 */
public abstract class SQLiteDatabase {

    private Plugin plugin = null;
    private Connection connection = null;
    private List<DatabaseModule> loadedModules;

    public SQLiteDatabase(@NotNull Plugin plugin) {
        setup(plugin);
    }

    public void setup(Plugin plugin) {
        this.plugin = plugin;
        initConnection();
        loadedModules = new ArrayList<>();
    }

    public Connection getConnection() {
        if (this.connection == null) {
            initConnection();
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
        if (loadedModules.contains(module)) {
            return;
        }
        module.init();
        loadedModules.add(module);
    }

    public List<DatabaseModule> getLoadedModules() {
        return loadedModules;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    private void initConnection() {

        // Make sure the data folder exists
        if (!FileUtils.createDirectory(this.plugin.getDataFolder())) {
            Loggers.error(this.plugin.getComponentLogger(), "Failed to create the plugin's data folder!");
            return;
        }

        // Try to connect to the SQLite database
        String url = "jdbc:sqlite:" + this.plugin.getDataFolder() + "/data.db";

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

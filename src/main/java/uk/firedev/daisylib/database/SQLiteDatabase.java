package uk.firedev.daisylib.database;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.utils.FileUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Helps with connecting to a SQLite Database
 */
public class SQLiteDatabase {

    private JavaPlugin plugin = null;
    private Connection connection = null;

    public SQLiteDatabase(@NotNull JavaPlugin plugin) {
        setup(plugin);
    }

    public void setup(JavaPlugin plugin) {
        this.plugin = plugin;
        initConnection();
    }

    public Connection getConnection() {
        if (this.connection == null) {
            initConnection();
        }
        return this.connection;
    }

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

    public JavaPlugin getPlugin() {
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

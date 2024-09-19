package main.java.com.baticuisine.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import main.java.com.baticuisine.config.AppConfig;

public class DatabaseConnection {
    private static Connection connection;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        // Load configuration
        AppConfig config = new AppConfig();
        URL = config.getProperty("db.url");
        USERNAME = config.getProperty("db.username");
        PASSWORD = config.getProperty("db.password");

        try {
            // Initialize the database connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database");
        }
    }

    private DatabaseConnection() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() {
        return connection;
    }
}

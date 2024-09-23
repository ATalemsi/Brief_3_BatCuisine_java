package main.java.com.baticuisine.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("main/resources/application.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find config.properties");
            }
            properties.load(input);
            String URL = properties.getProperty("db.url");
            String USER = properties.getProperty("db.username"); // Use db.username instead of db.user
            String PASSWORD = properties.getProperty("db.password");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to connect to the database");
        }
    }

    static {
        new DatabaseConnection(); // Initialize the connection when the class is loaded
    }

    public static Connection getConnection() {
        return connection;
    }
}

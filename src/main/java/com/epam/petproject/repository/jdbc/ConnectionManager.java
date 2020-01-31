package com.epam.petproject.repository.jdbc;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final String PROPERTY_PATH = "src/main/resources/properties/db.properties";
    private Connection connection = null;

    Connection getMYSQLConnection() {
         try(FileReader reader = new FileReader(PROPERTY_PATH)) {
            Properties properties = new Properties();
            properties.load(reader);
            String driver = properties.getProperty("jdbc.driver");
            String URL = properties.getProperty("db.url");
            String login = properties.getProperty("db.login");
            String pass = properties.getProperty("db.pass");

            Class.forName(driver);
            if(connection == null) {
                connection = DriverManager.getConnection(URL, login, pass);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getH2Connection() {
        try(FileReader reader = new FileReader(PROPERTY_PATH)) {
            Properties properties = new Properties();
            properties.load(reader);
            String driver = properties.getProperty("h2.driver");
            String URL = properties.getProperty("h2.url");
            String login = properties.getProperty("h2.login");
            String pass = properties.getProperty("h2.pass");

            Class.forName(driver);
            if(connection == null) {
                connection = DriverManager.getConnection(URL, login, pass);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

package com.epam.petproject.repository.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PROPERTY_PATH = "src/main/resources/properties/liquibase.properties";


    public DataSource getMySQLDataSource(){
        Properties properties = new Properties();
        FileInputStream inputStream;
        MysqlDataSource mysqlDS = null;
        try {
            inputStream = new FileInputStream(PROPERTY_PATH);
            properties.load(inputStream);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(properties.getProperty("url"));
            mysqlDS.setUser(properties.getProperty("username"));
            mysqlDS.setPassword(properties.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }

    public Connection getConnection() {
        try {
            return getMySQLDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}


package com.epam.petproject.repository.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionFactory {
    private static final String PROPERTY_PATH = "src/main/resources/properties/db.properties";

    DataSource getMySQLDataSource(){
        Properties properties = new Properties();
        FileInputStream inputStream;
        MysqlDataSource mysqlDS = null;
        try {
            inputStream = new FileInputStream(PROPERTY_PATH);
            properties.load(inputStream);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(properties.getProperty("db.url"));
            mysqlDS.setUser(properties.getProperty("db.login"));
            mysqlDS.setPassword(properties.getProperty("db.pass"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }

    public Connection getConnection(DataSource dataSource) {
        try {
            return getMySQLDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}


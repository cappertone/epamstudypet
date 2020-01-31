package com.epam.petproject.repository.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class ConnectionManagerTest {
   private Connection connection;



    @Test
    public void getMYSQLConnection() {
        try {
            ConnectionManager manager = new ConnectionManager();
            connection = manager.getMYSQLConnection();
            Statement statement = connection.createStatement();
            connection.close();
            assertTrue(statement.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getH2Connection() {
        try {
            ConnectionManager manager = new ConnectionManager();
            connection = manager.getH2Connection();
            Statement statement = connection.createStatement();
            assertFalse(statement.isClosed());
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
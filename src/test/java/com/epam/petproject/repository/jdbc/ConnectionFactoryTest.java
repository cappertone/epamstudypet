package com.epam.petproject.repository.jdbc;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConnectionFactoryTest {


    @Test
    public void getDatasource() {
        Connection connection = null;
        try{
            ConnectionFactory factory = new ConnectionFactory();
            DataSource ds = factory.getMySQLDataSource();
            connection = ds.getConnection();
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
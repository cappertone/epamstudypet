package com.epam.petproject.repository.jdbc;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;


public class JDBCAccountRepositotyImplTest {
    private Connection connection;
    private JdbcConnectionPool connectionPool;
    private static final String PROPERTY_PATH = "src/test/java/resources/db.properties";
    JDBCAccountRepositotyImpl repositoty;

    @Before
    public void init() throws SQLException {
        FileInputStream inputStream;
        Properties properties = new Properties();
        try {
            inputStream = new FileInputStream(PROPERTY_PATH);
            properties.load(inputStream);
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.login"),
                    properties.getProperty("db.pass"));
            connection.setCatalog("use studypet");
            connection.setAutoCommit(false);
//            connectionPool = JdbcConnectionPool.create(
//                    properties.getProperty("db.url"),
//                    properties.getProperty("db.login"),
//                    properties.getProperty("db.pass"));
//            connection = connectionPool.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getAll() {
        repositoty = new JDBCAccountRepositotyImpl(this.connection);
        assertNotNull(repositoty.getAll());
    }

    @Test
    public void update() throws SQLException {
        Account account = new Account(1L, AccountStatus.BANNED);
        repositoty = new JDBCAccountRepositotyImpl(connection);
        repositoty.update(account);
         assertEquals(account, repositoty.getById(1L));
         connection.rollback();
    }

    @Test
    public void deleteById() throws SQLException {
        repositoty = new JDBCAccountRepositotyImpl(connection);
        repositoty.deleteById(2L);
        assertNull(repositoty.getById(2L));
        connection.rollback();
    }

    @Test
    public void save() {

        Account account = new Account(10L, AccountStatus.ACTIVE);
        repositoty = new JDBCAccountRepositotyImpl(connection);
        //repositoty.save(account);
       // assertEquals(account, repositoty.getById(10L));
    }

    @Test
    public void getById() {
        JDBCAccountRepositotyImpl repositoty = new JDBCAccountRepositotyImpl(connection);
        assertNotNull(repositoty.getById(3L));
    }

    @After
    public void close() throws SQLException {
        connection.close();
        //connectionPool.dispose();
    }
}
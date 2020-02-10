package com.epam.petproject.repository.jdbc;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.model.Developer;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;


public class JDBCAccountRepositotyTest {
    private Connection connection;
    private static final String PROPERTY_PATH = "src/test/java/resources/db.properties";
    private JDBCAccountRepositoty repositoty;
    private DataSource dataSource;

    @Before
    public void init() throws SQLException {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(PROPERTY_PATH);
            Properties properties = new Properties();
            properties.load(inputStream);
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL(properties.getProperty("db.url"));
            dataSource.setUser(properties.getProperty("db.login"));
            dataSource.setPassword(properties.getProperty("db.pass"));
            this.dataSource = dataSource;
            connection = this.dataSource.getConnection();
            connection.setCatalog("use studypet");
            connection.setAutoCommit(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void getAll() {
        repositoty = new JDBCAccountRepositoty(dataSource);
        assertNotNull(repositoty.getAll());
    }

    @Test
    public void update() throws SQLException {
        Account account = new Account(1L, AccountStatus.BANNED);
        repositoty = new JDBCAccountRepositoty(dataSource);
        repositoty.update(account);
        assertEquals(account, repositoty.getById(1L));
        connection.rollback();
    }

    @Test
    public void deleteById() throws SQLException {
        repositoty = new JDBCAccountRepositoty(dataSource);
        repositoty.deleteById(2L);
        assertNull(repositoty.getById(2L));
        connection.rollback();
    }

    @Test
    public void save() {
        JDBCDeveloperRepository developerRepository = new JDBCDeveloperRepository(dataSource);
        Account account = new Account(10L, AccountStatus.ACTIVE);
        developerRepository.save(new Developer(10L, "Mike", null , account));
        repositoty = new JDBCAccountRepositoty(dataSource);
        repositoty.save(account);
        assertEquals(account, repositoty.getById(10L));
    }

    @Test
    public void getById() {
        repositoty = new JDBCAccountRepositoty(dataSource);
        assertNotNull(repositoty.getById(3L));
    }

    @After
    public void close() throws SQLException {
        connection.close();
    }
}
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

public class JDBCDeveloperRepositoryTest {
    private Connection connection;
    private static final String PROPERTY_PATH = "src/test/java/resources/db.properties";
    private JDBCDeveloperRepository repositoty;
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
        repositoty = new JDBCDeveloperRepository(dataSource);
        assertNotNull(repositoty.getAll());
    }

    @Test
    public void update()  {
        repositoty = new JDBCDeveloperRepository(dataSource);
        Account account = new Account(13L, AccountStatus.ACTIVE);

        Developer developer = new Developer(3L, "Barry", null , account);
        repositoty.update(developer);
        assertEquals("Barry", repositoty.getById(3L).getName());
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteById() {
        repositoty = new JDBCDeveloperRepository(dataSource);
        repositoty.deleteById(2L);
        assertNull(repositoty.getById(2L));
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void save() {
        repositoty = new JDBCDeveloperRepository(dataSource);
        Developer developer = new Developer(15L, "Miles", null , null);
        repositoty.save(developer);
        assertEquals(developer.getName(), repositoty.getById(15L).getName());
    }

    @Test
    public void getById() {
        JDBCDeveloperRepository repositoty = new JDBCDeveloperRepository(dataSource);
        repositoty.getById(1L);
    }
    @After
    public void close() throws SQLException {
        connection.close();
    }
}
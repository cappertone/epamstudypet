package com.epam.petproject.repository.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;
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


public class JDBCAccountRepositotyImplTest {
    private DataSource dataSource = new ConnectionFactory().getMySQLDataSource();
    private Connection connection;
    private static final String PROPERTY_PATH = "src/test/db.properties";

    @Before
    public void init() throws SQLException {
        connection = dataSource.getConnection();
    }

    public DataSource getH2DataSource(){
            Properties properties = new Properties();
            FileInputStream inputStream;
            JdbcDataSource h2ds = null;
            try {
                inputStream = new FileInputStream(PROPERTY_PATH);
                properties.load(inputStream);
                h2ds = new JdbcDataSource();
                h2ds.setURL(properties.getProperty("db.url"));
                h2ds.setUser(properties.getProperty("db.login"));
                h2ds.setPassword(properties.getProperty("db.pass"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return h2ds;
        }

    @Test
    public void getAll() {
        JDBCAccountRepositotyImpl repositoty = new JDBCAccountRepositotyImpl(connection);
            assertNotNull(repositoty.getAll());
    }

    @Test
    public void update() {
    }

    @Test
    public void deleteById() {
        JDBCAccountRepositotyImpl repositoty = new JDBCAccountRepositotyImpl(connection);
        repositoty.deleteById(1L);
        assertNull(repositoty.getById(1L));
    }

    @Test
    public void save() {
    }

    @Test
    public void getById() {
        JDBCAccountRepositotyImpl repositoty = new JDBCAccountRepositotyImpl(connection);
        assertNotNull(repositoty.getById(2L));
    }
    @After
    public void close() throws SQLException {
        connection.close();
    }
}
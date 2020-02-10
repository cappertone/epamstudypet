package com.epam.petproject.repository.jdbc;

import com.epam.petproject.model.Skill;
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

public class JDBCSkillRepositoryTest {
    private Connection connection;
    private static final String PROPERTY_PATH = "src/test/java/resources/db.properties";
    private JDBCSkillRepository repositoty;
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
        repositoty = new JDBCSkillRepository(dataSource);
        assertNotNull(repositoty.getAll());
    }

    @Test
    public void update() {
        Skill skill = new Skill(3L, "Slf4j");
        repositoty = new JDBCSkillRepository(dataSource);
        repositoty.update(skill);
        assertEquals(skill, repositoty.getById(3L));
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteById() {
        repositoty = new JDBCSkillRepository(dataSource);
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
        Skill skill = new Skill(11L, "jsp");
        repositoty = new JDBCSkillRepository(dataSource);
        repositoty.save(skill);
        assertEquals(skill, repositoty.getById(11L));
    }

    @Test
    public void getById() {
        repositoty = new JDBCSkillRepository(dataSource);
        assertNotNull(repositoty.getById(3L));

    }
    @After
    public void close() throws SQLException {
        connection.close();
    }
}
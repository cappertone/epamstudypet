package com.epam.petproject.repository.jdbc;

import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JDBCSkillRepository implements SkillRepository<Skill, Long> {
    private Connection connection;
    private DataSource dataSource;
    private Logger logger = LoggerFactory.getLogger(JDBCSkillRepository.class);


    public JDBCSkillRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> result = new LinkedList<>();
        String sql = "SELECT * FROM studypet.skills";

        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("skill_id");
                String name = resultSet.getString("name");
                result.add(new Skill(id, name));
            }
        } catch (SQLException e) {
            logger.error("cannot get all skills", e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Skill update(Skill skill) {
        String sql = "UPDATE studypet.skills SET name = ? WHERE skill_id =?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(2, skill.getSkillID());
            statement.setString(1, skill.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("cannot get update skill", e);
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public void deleteById(Long aLong) {
        String sql = "DELETE FROM studypet.skills WHERE skill_id = ?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setLong(1, aLong);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("cannot get update skill", e);
            e.printStackTrace();
        }
    }

    @Override
    public Skill save(Skill skill) {
        String sql = "INSERT INTO studypet.skills VALUES (?,?)";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            Long id = skill.getSkillID();
            if (id == null) {
                id = generateID();
            }
            statement.setLong(1, id);
            statement.setString(2, skill.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("cannot get update skill", e);
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public Skill getById(Long aLong) {

        String sql = "SELECT * FROM studypet.skills WHERE skill_id =?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();

            resultSet.first();
            long id = resultSet.getLong("skill_id");
            String name = resultSet.getString("name");
            return (new Skill(id, name));
        } catch (SQLException e) {
            logger.error("cannot get update skill", e);
            e.printStackTrace();
        }
        return null;
    }
}

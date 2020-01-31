package com.epam.petproject.repository.jdbc;

import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.SkillRepository;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JDBCSkillRepositoryImpl implements SkillRepository<Skill, Long> {
    private Connection connection;

    public JDBCSkillRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> result = new LinkedList<>();
        try {
            String sql = "SELECT * FROM studypet.skills";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long id = resultSet.getLong("skill_id");
                String name = resultSet.getString("name");
                result.add(new Skill(id, name));
            }
        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Skill update(Skill skill) {
        String sql;
        try {
            sql = "UPDATE studypet.skills SET name = ? WHERE skill_id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, skill.getSkillID());
            statement.setString(2, skill.getName());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public void deleteById(Long aLong) {
        try {
            String sql = "DELETE FROM studypet.skills WHERE skill_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            statement.setLong(1, aLong);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
    }

    @Override
    public Skill save(Skill skill) {
        try {
            Long id = skill.getSkillID();
            if (id == null) {
                id = generateID();
            }
            String sql = "INSERT INTO studypet.skills VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.setString(2, skill.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public Skill getById(Long aLong) {
        try {
            String sql = "SELECT * FROM studypet.skills WHERE skill_id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();

            resultSet.first();
            long id = resultSet.getLong("skill_id");
            String name = resultSet.getString("name");
            return (new Skill(id, name));
        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
        return null;
    }
}

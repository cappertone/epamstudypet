package com.epam.petproject.repository.jdbc;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.model.Developer;
import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.DeveloperRepository;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JDBCDeveloperRepository implements DeveloperRepository<Developer, Long> {
    private Connection connection;
    private DataSource dataSource;
    private Logger logger = LoggerFactory.getLogger(JDBCDeveloperRepository.class);


    public JDBCDeveloperRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Developer> getAll() {
        List<Developer> result = new LinkedList<>();
        @SuppressWarnings("SqlResolve") String sql = "SELECT ID FROM studypet.developers";

        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                result.add(getById(resultSet.getLong("ID")));
            }
        } catch (SQLException e) {
            logger.error("cannot get all developers", e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Developer update(Developer developer) {
        Developer oldDeveloper = getById(developer.getId());

        @SuppressWarnings("SqlResolve") String nameQuery = "UPDATE studypet.developers SET name = ? WHERE ID = ?";
        @SuppressWarnings("SqlResolve") String accountQuery = "UPDATE studypet.developers SET account_id = ? WHERE ID = ?";
        @SuppressWarnings("SqlResolve") String delSkillquery = "DELETE  FROM studypet.developer_skills WHERE developer_id = ?" +
                " AND skill_id =?";
        @SuppressWarnings("SqlResolve") String skillQuery = "INSERT INTO studypet.developer_skills (developer_id, skill_id) " +
                "VALUES (?,?)ON DUPLICATE KEY UPDATE developer_id = VALUES(developer_id),\n" +
                "skill_id = VALUES(skill_id);";

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statementName = connection.prepareStatement(nameQuery)) {


            if (!oldDeveloper.equals(developer)) {
                if (!oldDeveloper.getName().equals(developer.getName())) {

                    statementName.setString(1, developer.getName());
                    statementName.setLong(2, developer.getId());
                    statementName.executeUpdate();
                } else if (!oldDeveloper.getAccount().equals(developer.getAccount())) {
                    PreparedStatement statementAccount = connection.prepareStatement(accountQuery);
                    statementAccount.setLong(1, developer.getAccount().getAccountId());
                    statementAccount.setLong(2, developer.getId());
                    statementAccount.executeUpdate();
                } else {
                    PreparedStatement statementSkill = connection.prepareStatement(skillQuery);
                    statementSkill.setLong(1, developer.getId());
                    Set<Skill> oldSkillSet = oldDeveloper.getSkills();
                    Set<Skill> newSkillSet = developer.getSkills();

                    for (Skill skill : newSkillSet) {
                        if (!oldSkillSet.contains(skill)) {
                            statementSkill.setLong(2, skill.getSkillID());
                            statementSkill.executeUpdate();
                        }
                    }
                    for (Skill skill : oldSkillSet) {
                        if (!newSkillSet.contains(skill)) {
                            PreparedStatement statementDelSkill = connection.prepareStatement(delSkillquery);
                            statementDelSkill.setLong(1, developer.getId());
                            statementDelSkill.setLong(2, skill.getSkillID());
                            statementDelSkill.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("cannot update developer", e);
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public void deleteById(Long aLong) {
        @SuppressWarnings("SqlResolve") String sql = "DELETE FROM studypet.developers WHERE ID = ?";

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setLong(1, aLong);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("cannot delete developer", e);
            e.printStackTrace();
        }
    }

    @Override
    public Developer save(Developer developer) {
        @SuppressWarnings("SqlResolve") String sql = "INSERT INTO studypet.developers VALUES (?,?,?)";
        @SuppressWarnings("SqlResolve") String skillSetQuery = "INSERT INTO studypet.developer_skills VALUES(?,?)";
        @SuppressWarnings("SqlResolve") String accountQuery = "INSERT INTO studypet.accounts VALUES (?, ?)";

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            Long id = developer.getId();
            if (id == null) {
                id = generateID();
            }
            statement.setLong(1, id);
            statement.setString(2, developer.getName());
            Long accountId = null;
            Account account = developer.getAccount();
            if (account != null) {
                accountId = developer.getAccount().getAccountId();
                statement.setLong(3, accountId);
            } else {
                statement.setNull(3, Types.NULL);
            }
            statement.executeUpdate();
            if (developer.getSkills() != null) {
                PreparedStatement statementSkills = connection.prepareStatement(skillSetQuery);
                statementSkills.setLong(1, developer.getId());
                for (Skill skill : developer.getSkills()) {
                    statementSkills.setLong(2, skill.getSkillID());
                    statementSkills.executeUpdate();
                }
            }
            if (account != null) {
                PreparedStatement statementAcc = connection.prepareStatement(accountQuery);
                statementAcc.setLong(1, developer.getAccount().getAccountId());
                statementAcc.setString(2, developer.getAccount().getStatus().name());
                statementAcc.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("cannot save developer", e);
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public Developer getById(Long aLong) {
        @SuppressWarnings("SqlResolve") String sql = "SELECT id,name,status FROM studypet.developers LEFT JOIN studypet.accounts skills\n" +
                "ON developers.account_id = skills.account_id\n" +
                "WHERE developers.id = ?";
        @SuppressWarnings("SqlResolve") String getSkillsQuery = "SELECT studypet.skills.skill_id, name FROM studypet.developer_skills LEFT JOIN studypet.skills\n" +
                " ON skills.skill_id = developer_skills.skill_id\n" +
                "WHERE developer_skills.developer_id = ?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            long id = resultSet.getLong("ID");
            String name = resultSet.getString("name");
            String status = resultSet.getString("status");
            Account account;
            if (status != null) {
                account = new Account(aLong, AccountStatus.valueOf(status));
            } else {
                account = null;
            }
            PreparedStatement statement1 = connection.prepareStatement(getSkillsQuery);
            statement1.setLong(1, aLong);
            ResultSet resultSkillSet = statement1.executeQuery();
            Set<Skill> skillSet = new HashSet<>();
            while (resultSkillSet.next()) {
                skillSet.add(new Skill(
                        resultSkillSet.getLong("skill_id"),
                        resultSkillSet.getString("name")
                ));
            }
            return new Developer(id, name, skillSet, account);

        } catch (SQLException e) {
            logger.error("cannot get developer", e);
            e.printStackTrace();
            return null;
        }
    }
}

package com.epam.petproject.repository.jdbc;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.model.Developer;
import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.DeveloperRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JDBCDeveloperRepository implements DeveloperRepository<Developer, Long> {
    private Connection connection;
    private JDBCSkillRepositoryImpl skillRepository = new JDBCSkillRepositoryImpl(new ConnectionManager().getMYSQLConnection());

    public JDBCDeveloperRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Developer> getAll() {
        List<Developer> result = new LinkedList<>();
        try {
            @SuppressWarnings("SqlResolve") String sql = "SELECT ID FROM studypet.developers";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(getById(resultSet.getLong("ID")));
            }
        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Developer update(Developer developer) {
        Developer oldDeveloper = getById(developer.getId());

        @SuppressWarnings("SqlResolve") String nameQuery = "UPDATE developers SET name = ? WHERE ID = ?";
        @SuppressWarnings("SqlResolve") String accountQuery = "UPDATE developers SET account_id = ? WHERE ID = ?";
        @SuppressWarnings("SqlResolve") String delSkillquery = "DELETE  FROM `developer-skills` WHERE developer_id = ?" +
                " AND skill_id =?";
        @SuppressWarnings("SqlResolve") String skillQuery = "INSERT INTO `developer-skills` (developer_id, skill_id) " +
                "VALUES (?,?)ON DUPLICATE KEY UPDATE developer_id = VALUES(developer_id),\n" +
                "skill_id = VALUES(skill_id);";

        try {
            if (!oldDeveloper.equals(developer)) {
                if (!oldDeveloper.getName().equals(developer.getName())) {
                    PreparedStatement statementName = connection.prepareStatement(nameQuery);
                    statementName.setString(1, developer.getName());
                    statementName.setLong(2, developer.getId());
                    statementName.executeUpdate();
                } else if (!oldDeveloper.getAccount().equals(developer.getAccount())) {
                    PreparedStatement statementAccount = connection.prepareStatement(accountQuery);
                    statementAccount.setLong(1, developer.getAccount().getAccountId());
                    statementAccount.setLong(1, developer.getId());
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
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
        return developer;
    }

    public Set<Skill> getSkillSet(Developer developer) throws SQLException, ClassNotFoundException {
        @SuppressWarnings("SqlResolve") String skillquery = "SELECT skill_id FROM `developer-skills` WHERE developer_id = ?";
        PreparedStatement statementSkills = connection.prepareStatement(skillquery);
        statementSkills.setLong(1, developer.getId());
        ResultSet resultSet = statementSkills.executeQuery();
        Set<Skill> skillSet = new HashSet<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("skill_id");
            skillSet.add(skillRepository.getById(id));
        }
        return skillSet;
    }

    @Override
    public void deleteById(Long aLong) {
        try {
            @SuppressWarnings("SqlResolve")String sql = "DELETE FROM studypet.developers WHERE ID = ?";
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
    public Developer save(Developer developer) {
        try {
            Long id = developer.getId();
            if (id == null) {
                id = generateID();
            }
            @SuppressWarnings("SqlResolve") String sql = "INSERT INTO studypet.developers VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.setString(2, developer.getName());
            statement.setLong(3, developer.getAccount().getAccountId());
            statement.executeUpdate();

            @SuppressWarnings("SqlResolve") String skillSetQuery = "INSERT INTO studypet.`developer-skills` VALUES(?,?)";
            PreparedStatement statementSkill = connection.prepareStatement(skillSetQuery);
            statementSkill.setLong(1, developer.getId());
            for (Skill skill : developer.getSkills()) {
                statementSkill.setLong(2, skill.getSkillID());
                statementSkill.executeUpdate();
            }

            @SuppressWarnings("SqlResolve") String accountQuery = "INSERT INTO studypet.accounts VALUES (?, ?)";
            PreparedStatement statementAccount = connection.prepareStatement(accountQuery);
            statementAccount.setLong(1, developer.getAccount().getAccountId());
            statementAccount.setString(2, developer.getAccount().getStatus().name());

        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public Developer getById(Long aLong) {
        try {
            @SuppressWarnings("SqlResolve") String sql = "SELECT ID,name,status FROM developers LEFT JOIN accounts skills\n" +
                    "ON developers.account_id = skills.account_id\n" +
                    "WHERE developers.ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
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
            @SuppressWarnings("SqlResolve") String getSkillsQuery = "SELECT skills.skill_id, name FROM `developer-skills` LEFT JOIN skills\n" +
                    " ON skills.skill_id = `developer-skills`.skill_id\n" +
                    "WHERE `developer-skills`.developer_id = ?";
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
            System.out.println("smth wrong in sql");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        ConnectionManager connectionManager = new ConnectionManager();
        JDBCDeveloperRepository developerRepository = new JDBCDeveloperRepository(connectionManager.getMYSQLConnection());


        developerRepository.getAll();
        Developer developer = developerRepository.getById(3L);
        JDBCSkillRepositoryImpl repository = new JDBCSkillRepositoryImpl(connectionManager.getMYSQLConnection());
        Skill sk = repository.getById(2L);
        Set<Skill> devsk = developer.getSkills();
        devsk.remove(sk);
        developer.setSkills(devsk);
        developerRepository.update(developer);


    }
}

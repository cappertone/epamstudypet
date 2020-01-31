package com.epam.petproject.repository.jdbc;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.repository.AccountRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class JDBCAccountRepositotyImpl implements AccountRepository<Account, Long> {
    private Connection connection;

    public JDBCAccountRepositotyImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> getAll() {
        List<Account> result = new LinkedList<>();
        try {
            String sql = "SELECT * FROM studypet.accounts";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long id = resultSet.getLong("acccount_id");
                AccountStatus status = AccountStatus.valueOf(resultSet.getString("status"));
                String name = resultSet.getString("status");
                result.add(new Account(id, status));
            }
        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Account update(Account account) {
            try {
                @SuppressWarnings("SqlResolve")String sql = "UPDATE studypet.accounts SET status = ? WHERE developer_id =?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, account.getAccountId());
                statement.setString(2, account.getStatus().name());
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return account;
    }

    @Override
    public void deleteById(Long aLong) {
        try {
            @SuppressWarnings("SqlResolve")String sql = "DELETE FROM studypet.accounts WHERE developer_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            statement.setLong(1, aLong);
            statement.executeUpdate();
            //connection.commit();
        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
    }

    @Override
    public Account save(Account account) {
        try {
            Long id = account.getAccountId();
            String sql = "INSERT INTO studypet.accounts VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.setString(2, account.getStatus().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account getById(Long aLong) {
        Long id = null;
        AccountStatus status = null;
        try {
            @SuppressWarnings("SqlResolve") String sql = "SELECT * FROM studypet.accounts WHERE developer_id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            id = resultSet.getLong("account_id");
            status = AccountStatus.valueOf(resultSet.getString("status"));
        } catch (SQLException e) {
            System.out.println("smth wrong in sql");
            e.printStackTrace();
        }
        return (new Account(id, status));
    }
}

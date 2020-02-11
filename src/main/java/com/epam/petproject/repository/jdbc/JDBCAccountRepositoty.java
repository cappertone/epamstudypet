package com.epam.petproject.repository.jdbc;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.repository.AccountRepository;
import com.google.gson.Gson;
import jdk.nashorn.internal.scripts.JD;
import lombok.extern.slf4j.Slf4j;
import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
@Slf4j
public class JDBCAccountRepositoty implements AccountRepository<Account, Long> {
    private DataSource dataSource;


    public JDBCAccountRepositoty(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Account> getAll() {
        String sql = "SELECT * FROM studypet.accounts";
        List<Account> result = new LinkedList<>();
        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("account_id");
                AccountStatus status = AccountStatus.valueOf(resultSet.getString("status"));
                result.add(new Account(id, status));
            }
        } catch (SQLException e) {
            log.error("cannot select all accounts", e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Account update(Account account) {
        String sql = "UPDATE studypet.accounts SET status = ? WHERE account_id =?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(2, account.getAccountId());
            statement.setString(1, account.getStatus().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("cannot update account", e);
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void deleteById(Long aLong) {
        String sql = "DELETE FROM studypet.accounts WHERE account_id = ?";

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setLong(1, aLong);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("cannot delete account", e);
            e.printStackTrace();
        }
    }

    @Override
    public Account save(Account account) {
        Long id = account.getAccountId();
        String sql = "INSERT INTO studypet.accounts VALUES (?,?)";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setString(2, account.getStatus().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("cannot save account", e);
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account getById(Long aLong) {
        Long id;
        AccountStatus status;
        Account result = null;
        String sql = "SELECT * FROM studypet.accounts WHERE account_id =?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                id = resultSet.getLong("account_id");
                status = AccountStatus.valueOf(resultSet.getString("status"));
                result = new Account(id, status);
            }
        } catch (SQLException e) {
            log.error("cannot get account by id", e);
            e.printStackTrace();
        }
        return result;
    }


}

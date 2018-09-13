package com.revolut.skiftcha.dao;

import com.revolut.skiftcha.db.DataSource;
import com.revolut.skiftcha.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    private final Connection connection;

    public AccountDAOImpl() throws SQLException {
        connection = DataSource.getConnection();
        connection.setAutoCommit(false);
    }

    @Override
    public List<Account> listAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(
                     "select id, balance from account")) {
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setBalance(rs.getInt("balance"));
                accounts.add(account);
            }
            return accounts;
        }
    }

    @Override
    public Integer getBalance(int id) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(
                     "select balance from account where id = ? for update")) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("balance");
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public void setBalance(int id, int balance) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(
                     "update account set  balance = ? where id = ?")) {
            pst.setInt(1, balance);
            pst.setInt(2, id);
            pst.executeUpdate();
        }
    }

    @Override
    public void close() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }
}

package com.revolut.skiftcha.dao;

import com.revolut.skiftcha.model.Account;
import com.revolut.skiftcha.model.HistoryRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class AccountDAOImpl implements AccountDAO {

    private final Connection connection;

    public AccountDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Account> listAccounts() throws SQLException {
        Collection<Account> accounts = new ArrayList<>();
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
    public Collection<HistoryRecord> getHistory() throws SQLException {
        Collection<HistoryRecord> history = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(
                     "select account_from, account_to, amount, time from balance_history")) {
            while (rs.next()) {
                HistoryRecord record = new HistoryRecord();
                record.setFrom(rs.getInt("account_from"));
                if (rs.wasNull()) {
                    record.setFrom(null);
                }
                record.setTo(rs.getInt("account_to"));
                if (rs.wasNull()) {
                    record.setTo(null);
                }
                record.setAmount(rs.getInt("amount"));
                record.setTime(new Date(rs.getTimestamp("time").getTime()));
                history.add(record);
            }
            return history;
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
    public void saveHistory(Integer from, Integer to, int amount) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(
                "insert into balance_history(account_from, account_to, amount, time) values (?, ?, ?, ?)")) {
            pst.setObject(1, from);
            pst.setObject(2, to);
            pst.setInt(3, amount);
            pst.setTimestamp(4, Timestamp.from(Instant.now()));
            pst.executeUpdate();
        }
    }

    @Override
    public int createAccount() throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(
                "insert into account(id, balance) values (default, ?)", Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, 0);
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new SQLException("Cannot create account");
                }
            }
        }
    }
}

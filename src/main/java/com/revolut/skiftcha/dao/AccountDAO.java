package com.revolut.skiftcha.dao;

import com.revolut.skiftcha.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO extends AutoCloseable {
    List<Account> listAccounts() throws SQLException;
    Integer getBalance(int id) throws SQLException;
    void setBalance(int id, int amount) throws SQLException;
    void commit() throws SQLException;
}

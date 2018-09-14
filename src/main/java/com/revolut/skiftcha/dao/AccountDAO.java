package com.revolut.skiftcha.dao;

import com.revolut.skiftcha.model.Account;

import java.sql.SQLException;
import java.util.Collection;

public interface AccountDAO {
    Collection<Account> listAccounts() throws SQLException;
    Integer getBalance(int id) throws SQLException;
    void setBalance(int id, int amount) throws SQLException;
}

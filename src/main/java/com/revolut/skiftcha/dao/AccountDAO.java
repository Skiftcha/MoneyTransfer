package com.revolut.skiftcha.dao;

import com.revolut.skiftcha.model.Account;
import com.revolut.skiftcha.model.HistoryRecord;

import java.sql.SQLException;
import java.util.Collection;

public interface AccountDAO {
    Collection<Account> listAccounts() throws SQLException;
    Collection<HistoryRecord> getHistory() throws SQLException;
    Integer getBalance(int id) throws SQLException;
    void setBalance(int id, int amount) throws SQLException;
    void saveHistory(Integer from, Integer to, int amount) throws SQLException;
}

package com.revolut.skiftcha.service;

import com.revolut.skiftcha.model.Account;

import java.util.Collection;

public interface AccountService {
    Collection<Account> listAccounts();
    int getBalance(int id);
    void deposit(int id, int amount);
    void withdraw(int id, int amount);
    void transfer(int from, int to, int amount);
}

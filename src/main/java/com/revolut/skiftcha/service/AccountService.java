package com.revolut.skiftcha.service;

import com.revolut.skiftcha.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> listAccounts();
    int getBalance(int id);
    void deposit(int id, int amount);
    void withdraw(int id, int amount);
    void transfer(int from, int to, int amount);
}

package com.revolut.skiftcha.controller;

import com.revolut.skiftcha.service.AccountService;
import com.revolut.skiftcha.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountsController {
    private static final Logger LOG = LoggerFactory.getLogger(AccountsController.class);

    private AccountService service = new AccountServiceImpl();

    public void listAccounts() {
        service.listAccounts().forEach(account -> LOG.debug("id {} balance {}", account.getId(), account.getBalance()));
    }
}

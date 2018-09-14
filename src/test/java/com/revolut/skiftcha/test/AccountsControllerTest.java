package com.revolut.skiftcha.test;

import com.revolut.skiftcha.controller.AccountsController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountsControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(AccountsControllerTest.class);

    private AccountsController controller = new AccountsController();

    @Test
    public void testListAccounts() {
        controller.listAccounts().forEach(account -> {
            LOG.info("accountId {} balance {}", account.getId(), account.getBalance());
        });
    }
}

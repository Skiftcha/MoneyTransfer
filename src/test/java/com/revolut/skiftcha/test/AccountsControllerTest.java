package com.revolut.skiftcha.test;

import com.revolut.skiftcha.controller.AccountsController;
import org.junit.Test;

public class AccountsControllerTest {
    private AccountsController controller = new AccountsController();

    @Test
    public void testListAccounts() {
        controller.listAccounts();
    }
}

package com.revolut.skiftcha.test;

import com.revolut.skiftcha.controller.AccountsController;
import com.revolut.skiftcha.model.AccountRequest;
import com.revolut.skiftcha.model.HistoryRecord;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class AccountsControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(AccountsControllerTest.class);

    private AccountsController controller;

    @Before
    public void resetController() {
        controller = new AccountsController();
    }

    @Test
    public void testListAccounts() {
        controller.createAccount();
        controller.createAccount();
        controller.createAccount();
        assertEquals(3, controller.listAccounts().size());
    }

    @Test
    public void testDepositHistory() {
        int id = controller.createAccount();

        AccountRequest request = new AccountRequest();
        request.setId(id);
        request.setAmount(10);
        controller.deposit(request);

        Collection<HistoryRecord> records = controller.getHistory();
        records.forEach(record -> {
            LOG.info("history record from {} to {} amount {} datetime {}",
                    record.getFrom(), record.getTo(), record.getAmount(), record.getTime());
        });
    }
}

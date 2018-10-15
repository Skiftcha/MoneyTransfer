package com.revolut.skiftcha.test;

import com.revolut.skiftcha.model.Account;
import com.revolut.skiftcha.model.AccountRequest;
import com.revolut.skiftcha.model.HistoryRecord;
import com.revolut.skiftcha.model.TransferRequest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public abstract class AbstractAccountsControllerTest {

    @Test
    public void testListAccounts() {
        int countBefore = listAccounts().size();
        List<Integer> ids = Arrays.asList(
                createAccount(),
                createAccount(),
                createAccount());
        int countAfter = listAccounts().size();
        assertEquals(countBefore + 3, countAfter);
        listAccounts().stream()
                .filter(account -> ids.contains(account.getId()))
                .forEach(account -> assertEquals(0, account.getBalance()));
    }

    @Test
    public void testDeposit() {
        int id = createAccount();
        assertEquals(0, getBalance(id));
        AccountRequest request = new AccountRequest();
        request.setId(id);
        request.setAmount(10);
        deposit(request);
        assertEquals(10, getBalance(id));
        List<HistoryRecord> records = getHistory().stream()
                .filter(record -> record.getTo() != null && record.getTo() == id)
                .collect(Collectors.toList());
        assertEquals(1, records.size());
        HistoryRecord record = records.get(0);
        assertNull(record.getFrom());
        assertEquals(10, record.getAmount());
        assertNotNull(record.getTime());
    }

    @Test
    public void testWithdraw() {
        int id = createAccount();
        AccountRequest request = new AccountRequest();
        request.setId(id);
        request.setAmount(10);
        deposit(request);
        assertEquals(10, getBalance(id));
        request.setAmount(5);
        withdraw(request);
        assertEquals(5, getBalance(id));
        List<HistoryRecord> records = getHistory().stream()
                .filter(record -> record.getFrom() != null && record.getFrom() == id)
                .collect(Collectors.toList());
        assertEquals(1, records.size());
        HistoryRecord record = records.get(0);
        assertNull(record.getTo());
        assertEquals(5, record.getAmount());
        assertNotNull(record.getTime());
    }

    @Test
    public void testTransfer() {
        int id1 = createAccount();
        int id2 = createAccount();
        assertEquals(0, getBalance(id1));
        assertEquals(0, getBalance(id2));
        AccountRequest request = new AccountRequest();
        request.setId(id1);
        request.setAmount(20);
        deposit(request);
        request.setId(id2);
        request.setAmount(20);
        deposit(request);
        assertEquals(20, getBalance(id1));
        assertEquals(20, getBalance(id2));
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFrom(id1);
        transferRequest.setTo(id2);
        transferRequest.setAmount(7);
        transfer(transferRequest);
        assertEquals(13, getBalance(id1));
        assertEquals(27, getBalance(id2));
        List<HistoryRecord> records = getHistory().stream()
                .filter(record -> record.getFrom() != null && record.getFrom() == id1)
                .filter(record -> record.getTo() != null && record.getTo() == id2)
                .collect(Collectors.toList());
        assertEquals(1, records.size());
        HistoryRecord record = records.get(0);
        assertEquals(7, record.getAmount());
        assertNotNull(record.getTime());
    }

    protected abstract Collection<Account> listAccounts();

    protected abstract Collection<HistoryRecord> getHistory();

    protected abstract int createAccount();

    protected abstract int getBalance(int id);

    protected abstract void deposit(AccountRequest request);

    protected abstract void withdraw(AccountRequest request);

    protected abstract void transfer(TransferRequest transferRequest);
}

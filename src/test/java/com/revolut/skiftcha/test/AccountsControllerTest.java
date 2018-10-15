package com.revolut.skiftcha.test;

import com.revolut.skiftcha.controller.AccountsController;
import com.revolut.skiftcha.model.Account;
import com.revolut.skiftcha.model.AccountRequest;
import com.revolut.skiftcha.model.HistoryRecord;
import com.revolut.skiftcha.model.TransferRequest;

import java.util.Collection;

public class AccountsControllerTest extends AbstractAccountsControllerTest {

    private AccountsController controller = new AccountsController();

    @Override
    protected Collection<Account> listAccounts() {
        return controller.listAccounts();
    }

    @Override
    protected Collection<HistoryRecord> getHistory() {
        return controller.getHistory();
    }

    @Override
    protected int createAccount() {
        return controller.createAccount();
    }

    @Override
    protected int getBalance(int id) {
        return controller.getBalance(id);
    }

    @Override
    protected void deposit(AccountRequest request) {
        controller.deposit(request);
    }

    @Override
    protected void withdraw(AccountRequest request) {
        controller.withdraw(request);
    }

    @Override
    protected void transfer(TransferRequest transferRequest) {
        controller.transfer(transferRequest);
    }
}

package com.revolut.skiftcha.controller;

import com.revolut.skiftcha.model.Account;
import com.revolut.skiftcha.service.AccountService;
import com.revolut.skiftcha.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountsController {
    private static final Logger LOG = LoggerFactory.getLogger(AccountsController.class);

    private AccountService service = new AccountServiceImpl();

    @GET
    public Collection<Account> listAccounts() {
        return service.listAccounts();
    }

    @GET
    @Path("/{id}")
    public int getBalance(@PathParam("id") int id) {
        return service.getBalance(id);
    }

    public void deposit(int id, int amount) {

    }

    public void withdraw(int id, int amount) {

    }

    public void transfer(int from, int to, int amount) {

    }
}

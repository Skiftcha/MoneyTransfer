package com.revolut.skiftcha.controller;

import com.revolut.skiftcha.model.Account;
import com.revolut.skiftcha.model.AccountRequest;
import com.revolut.skiftcha.model.HistoryRecord;
import com.revolut.skiftcha.model.TransferRequest;
import com.revolut.skiftcha.service.AccountService;
import com.revolut.skiftcha.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("accounts")
public class AccountsController {
    private static final Logger LOG = LoggerFactory.getLogger(AccountsController.class);

    private AccountService service = new AccountServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Account> listAccounts() {
        LOG.debug("get accounts");
        return service.listAccounts();
    }

    @GET
    @Path("history")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<HistoryRecord> getHistory() {
        LOG.debug("get history");
        return service.getHistory();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public int getBalance(@PathParam("id") int id) {
        LOG.debug("get balance {}", id);
        return service.getBalance(id);
    }

    @POST
    @Path("deposit")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deposit(AccountRequest request) {
        LOG.debug("deposit id {} amount {}", request.getId(), request.getAmount());
        service.deposit(request.getId(), request.getAmount());
    }

    @POST
    @Path("withdraw")
    @Consumes(MediaType.APPLICATION_JSON)
    public void withdraw(AccountRequest request) {
        LOG.debug("withdraw id {} amount {}", request.getId(), request.getAmount());
        service.withdraw(request.getId(), request.getAmount());
    }

    @POST
    @Path("transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    public void transfer(TransferRequest request) {
        LOG.debug("transfer from {} to {} amount {}", request.getFrom(), request.getTo(), request.getAmount());
        service.transfer(request.getFrom(), request.getTo(), request.getAmount());
    }
}

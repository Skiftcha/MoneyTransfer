package com.revolut.skiftcha.test;

import com.revolut.skiftcha.model.Account;
import com.revolut.skiftcha.model.AccountRequest;
import com.revolut.skiftcha.model.HistoryRecord;
import com.revolut.skiftcha.model.TransferRequest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static org.junit.Assert.assertEquals;

public class RESTControllerTest extends AbstractAccountsControllerTest{

    private Client client = ClientBuilder.newClient();
    private WebTarget webTarget = client.target("http://localhost:8080/accounts");

    @Override
    protected Collection<Account> listAccounts() {
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(new GenericType<Collection<Account>>() {});
    }

    @Override
    protected Collection<HistoryRecord> getHistory() {
        Invocation.Builder invocationBuilder = webTarget.path("history").request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(new GenericType<Collection<HistoryRecord>>() {});
    }

    @Override
    protected int createAccount() {
        Invocation.Builder invocationBuilder = webTarget.path("create").request(MediaType.TEXT_PLAIN);
        return invocationBuilder.post(Entity.json(null), Integer.class);
    }

    @Override
    protected int getBalance(int id) {
        Invocation.Builder invocationBuilder = webTarget.path("{id}").resolveTemplate("id", id)
                .request(MediaType.TEXT_PLAIN);
        return invocationBuilder.get(Integer.class);
    }

    @Override
    protected void deposit(AccountRequest request) {
        Invocation.Builder invocationBuilder = webTarget.path("deposit").request(MediaType.APPLICATION_JSON);
        assertEquals(HTTP_NO_CONTENT,
                invocationBuilder.put(Entity.json(request)).getStatus());
    }

    @Override
    protected void withdraw(AccountRequest request) {
        Invocation.Builder invocationBuilder = webTarget.path("withdraw").request(MediaType.APPLICATION_JSON);
        assertEquals(HTTP_NO_CONTENT,
                invocationBuilder.put(Entity.json(request)).getStatus());
    }

    @Override
    protected void transfer(TransferRequest transferRequest) {
        Invocation.Builder invocationBuilder = webTarget.path("transfer").request(MediaType.APPLICATION_JSON);
        assertEquals(HTTP_NO_CONTENT,
                invocationBuilder.put(Entity.json(transferRequest)).getStatus());
    }
}

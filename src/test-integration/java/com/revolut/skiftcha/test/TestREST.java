package com.revolut.skiftcha.test;

import com.revolut.skiftcha.model.Account;
import com.revolut.skiftcha.model.AccountRequest;
import com.revolut.skiftcha.model.HistoryRecord;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static org.junit.Assert.assertEquals;

public class TestREST {
    private static final Logger LOG = LoggerFactory.getLogger(AccountsControllerTest.class);

    private Client client = ClientBuilder.newClient();
    private WebTarget webTarget = client.target("http://localhost:8080/accounts");

    @Test
    public void testListAccounts() {
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Collection<Account> accounts = invocationBuilder.get(new GenericType<Collection<Account>>() {});
        accounts.forEach(account -> {
            LOG.info("accountId {} balance {}", account.getId(), account.getBalance());
        });
    }

    @Test
    public void testDepositHistory() {
        Invocation.Builder invocationBuilder = webTarget.path("deposit").request(MediaType.APPLICATION_JSON);
        AccountRequest request = new AccountRequest();
        request.setId(1);
        request.setAmount(10);
        Response response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));
        assertEquals(response.getStatus(), HTTP_NO_CONTENT);

        invocationBuilder = webTarget.path("history").request(MediaType.APPLICATION_JSON);
        Collection<HistoryRecord> records = invocationBuilder.get(new GenericType<Collection<HistoryRecord>>() {});
        records.forEach(record -> {
            LOG.info("history record from {} to {} amount {} datetime {}",
                    record.getFrom(), record.getTo(), record.getAmount(), record.getTime());
        });
    }
}

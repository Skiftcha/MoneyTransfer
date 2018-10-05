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

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static org.junit.Assert.assertEquals;

public class TestREST {
    private static final Logger LOG = LoggerFactory.getLogger(AccountsControllerTest.class);

    private Client client = ClientBuilder.newClient();
    private WebTarget webTarget = client.target("http://localhost:8080/accounts");

    @Test
    public void testListAccounts() {
        int countBefore = listAccounts().size();
        createAccount();
        createAccount();
        createAccount();
        int countAfter = listAccounts().size();
        assertEquals(countBefore + 3, countAfter);
    }

    @Test
    public void testDepositHistory() {
        int id = createAccount();

        Response response = deposit(id, 10);
        assertEquals(response.getStatus(), HTTP_NO_CONTENT);

        Collection<HistoryRecord> records = getHistory();

        records.forEach(record -> {
            LOG.info("history record from {} to {} amount {} datetime {}",
                    record.getFrom(), record.getTo(), record.getAmount(), record.getTime());
        });
    }

    private Collection<Account> listAccounts() {
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(new GenericType<Collection<Account>>() {});
    }

    private int createAccount() {
        Invocation.Builder invocationBuilder = webTarget.path("create").request(MediaType.TEXT_PLAIN);
        return invocationBuilder.get(Integer.class);
    }

    private Response deposit(int id, int amount) {
        Invocation.Builder invocationBuilder = webTarget.path("deposit").request(MediaType.APPLICATION_JSON);
        AccountRequest request = new AccountRequest();
        request.setId(id);
        request.setAmount(amount);
        return invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));
    }

    private Collection<HistoryRecord> getHistory() {
        Invocation.Builder invocationBuilder = webTarget.path("history").request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(new GenericType<Collection<HistoryRecord>>() {});
    }
}

package com.revolut.skiftcha.test;

import com.revolut.skiftcha.model.Account;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

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
}

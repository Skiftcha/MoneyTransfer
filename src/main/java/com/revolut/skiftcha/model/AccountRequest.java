package com.revolut.skiftcha.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccountRequest {
    private int id;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

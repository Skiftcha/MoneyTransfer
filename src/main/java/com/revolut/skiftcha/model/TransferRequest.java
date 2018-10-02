package com.revolut.skiftcha.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransferRequest {
    private int from;
    private int to;
    private int amount;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

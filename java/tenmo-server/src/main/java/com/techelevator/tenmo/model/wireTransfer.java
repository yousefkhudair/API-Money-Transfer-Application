package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class wireTransfer {
    private int id;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username;}
}

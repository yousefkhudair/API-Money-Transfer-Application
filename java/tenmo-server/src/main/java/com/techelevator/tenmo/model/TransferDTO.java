package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDTO {
    private int sendTo;
    private int sendFrom;
    private BigDecimal amount;

    public int getSendTo() {
        return sendTo;
    }

    public void setSendTo(int sendTo) {
        this.sendTo = sendTo;
    }

    public int getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(int sendFrom) {
        this.sendFrom = sendFrom;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "sendTo=" + sendTo +
                ", sendFrom=" + sendFrom +
                ", amount=" + amount +
                '}';
    }
}

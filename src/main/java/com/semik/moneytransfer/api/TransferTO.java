package com.semik.moneytransfer.api;

public class TransferTO {
    private long sourceAccountId;

    private long destinationAccountId;

    private long cents;

    public long getSourceAccountId() {
        return sourceAccountId;
    }

    public long getDestinationAccountId() {
        return destinationAccountId;
    }

    public long getCents() {
        return cents;
    }
}

package com.semik.moneytransfer.account.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class AccountState {
    private @NonNull
    String accountId;
    private long balanceInCents;


    public AccountState(Account account) {
        this.accountId = account.getId();
        this.balanceInCents = account.getBalanceInCents();
    }

}

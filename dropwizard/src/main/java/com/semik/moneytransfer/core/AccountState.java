package com.semik.moneytransfer.core;

import javax.persistence.Embeddable;

@Embeddable
public class AccountState {
    private Long accountId;

    private long balanceInCents;

    public AccountState() {
    }

    public AccountState(Account account) {
        this.accountId = account.getId();
        this.balanceInCents = account.getBalanceInCents();
    }

}

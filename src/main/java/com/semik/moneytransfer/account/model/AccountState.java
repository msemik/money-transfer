package com.semik.moneytransfer.account.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Embeddable;

@Embeddable
@Data
public final class AccountState {
    private @NonNull Long accountId;

    private long balanceInCents;


    public AccountState(Account account) {
        this.accountId = account.getId();
        this.balanceInCents = account.getBalanceInCents();
    }

}

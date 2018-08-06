package com.semik.moneytransfer.account;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class AccountState {
    private @NonNull Long accountId;

    private long balanceInCents;


    public AccountState(Account account) {
        this.accountId = account.getId();
        this.balanceInCents = account.getBalanceInCents();
    }

}

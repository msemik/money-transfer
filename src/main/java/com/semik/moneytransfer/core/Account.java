package com.semik.moneytransfer.core;

import com.semik.moneytransfer.core.exception.NoSufficientMeansException;
import org.apache.commons.math3.util.ArithmeticUtils;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Embeddable
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    private long balanceInCents = 0;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public long getBalanceInCents() {
        return balanceInCents;
    }

    public void setBalanceInCents(long balanceInCents) {
        this.balanceInCents = balanceInCents;
    }

    public void charge(long cents) {
        if (balanceInCents - cents < 0) {
            throw new NoSufficientMeansException(balanceInCents, cents);
        }

        balanceInCents = ArithmeticUtils.addAndCheck(balanceInCents, -cents);
    }

    public void credit(long cents) {
        balanceInCents = ArithmeticUtils.addAndCheck(balanceInCents, cents);
    }

    public AccountState toAccountState() {
        return new AccountState(this);
    }
}

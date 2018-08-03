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

    private String firstName;

    private String surname;

    private long balanceInCents = 0;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

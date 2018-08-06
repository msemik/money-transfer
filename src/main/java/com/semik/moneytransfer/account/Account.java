package com.semik.moneytransfer.account;

import com.semik.moneytransfer.transfer.NoSufficientMeansException;
import lombok.Data;
import org.apache.commons.math3.util.ArithmeticUtils;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Embeddable
@Data
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String surname;

    private long balanceInCents = 0;

    public Account() {
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

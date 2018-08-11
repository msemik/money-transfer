package com.semik.moneytransfer.account.model;

import com.semik.moneytransfer.transfer.exception.NoSufficientMeansException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.StringJoiner;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "accounts")
public final class Account {
    @Id
    private String id;

    private String firstName;

    private String surname;

    private long balanceInCents = 0;

    @Version
    private Long version;

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

    @Override
    public String toString() {
        return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("surname='" + surname + "'")
                .add("balanceInCents=" + balanceInCents)
                .add("version=" + version)
                .toString();
    }
}

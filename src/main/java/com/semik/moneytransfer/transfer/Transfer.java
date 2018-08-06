package com.semik.moneytransfer.transfer;

import com.semik.moneytransfer.account.Account;
import com.semik.moneytransfer.account.AccountState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NamedQuery(name = "com.semik.moneytransfer.getAll", query = "from Transfer")
@Data
@NoArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "accountId", column = @Column(name = "source_id")),
            @AttributeOverride(name = "balanceInCents", column = @Column(name = "source_balance_in_cents"))})
    private AccountState source;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "accountId", column = @Column(name = "destination_id")),
            @AttributeOverride(name = "balanceInCents", column = @Column(name = "destination_balance_in_cents"))})
    private AccountState destination;

    private long cents;

    public void exchange(Account sourceAccount, Account destinationAccount, long cents) {
        this.source = sourceAccount.toAccountState();
        this.destination = destinationAccount.toAccountState();
        this.cents = cents;
        validateTransferredAmount(cents);
        sourceAccount.charge(cents);
        destinationAccount.credit(cents);
    }

    private void validateTransferredAmount(long cents) {
        if(cents <= 0) {
            throw new TransferAmountMustBePositiveNumberException();
        }
    }
}

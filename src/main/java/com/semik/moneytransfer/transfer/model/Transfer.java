package com.semik.moneytransfer.transfer.model;

import com.semik.moneytransfer.account.model.Account;
import com.semik.moneytransfer.account.model.AccountState;
import com.semik.moneytransfer.transfer.exception.TransferAmountMustBePositiveNumberException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NamedQuery(name = "com.semik.moneytransfer.getAll", query = "from Transfer")
@Data
public final class Transfer {

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

    private Transfer(Account sourceAccount, Account destinationAccount, long cents) {
        this.source = sourceAccount.toAccountState();
        this.destination = destinationAccount.toAccountState();
        this.cents = cents;
    }

    public static Transfer exchange(Account sourceAccount, Account destinationAccount, long cents) {
        Transfer transfer = new Transfer(sourceAccount, destinationAccount, cents);
        transfer.validateTransferredAmount(cents);
        sourceAccount.charge(cents);
        destinationAccount.credit(cents);
        return transfer;
    }

    private void validateTransferredAmount(long cents) {
        if(cents <= 0) {
            throw new TransferAmountMustBePositiveNumberException();
        }
    }
}

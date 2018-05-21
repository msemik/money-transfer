package com.semik.moneytransfer.core;

import com.semik.moneytransfer.api.TransferTO;
import com.semik.moneytransfer.core.exception.TransferAmountMustBePositiveNumberException;

import javax.persistence.*;

@Entity
@NamedQuery(name = "com.semik.moneytransfer.getAll", query = "from Transfer")
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

    public Transfer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountState getSource() {
        return source;
    }

    public void setSource(AccountState source) {
        this.source = source;
    }

    public AccountState getDestination() {
        return destination;
    }

    public void setDestination(AccountState destination) {
        this.destination = destination;
    }

    public long getCents() {
        return cents;
    }

    public void setCents(long cents) {
        this.cents = cents;
    }

    public void exchange(TransferTO transferTO) {
    }

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

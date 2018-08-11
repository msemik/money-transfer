package com.semik.moneytransfer.transfer.model;

import com.semik.moneytransfer.account.model.Account;
import com.semik.moneytransfer.account.model.AccountState;
import com.semik.moneytransfer.transfer.exception.TransferAmountMustBePositiveNumberException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;


@Document(collection = "transfers")
@Data
public final class Transfer {

    @Id
    private String id;

    private AccountState source;

    private AccountState destination;

    private long cents;

    @Version
    private Long version;

    private Transfer(Account sourceAccount, Account destinationAccount, long cents) {
        this.source = sourceAccount.toAccountState();
        this.destination = destinationAccount.toAccountState();
        this.cents = cents;
    }

    public static Mono<Transfer> exchange(Account sourceAccount, Account destinationAccount, long cents) {
        Transfer transfer = new Transfer(sourceAccount, destinationAccount, cents);
        transfer.validate();
        sourceAccount.charge(cents);
        destinationAccount.credit(cents);
        return Mono.just(transfer);
    }

    private void validate() {
        if (cents <= 0) {
            throw new TransferAmountMustBePositiveNumberException();
        }
    }
}

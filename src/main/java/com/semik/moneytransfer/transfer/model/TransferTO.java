package com.semik.moneytransfer.transfer.model;

import com.semik.moneytransfer.crosscutting.constraint.differentfieldvalues.DifferentFieldValues;
import lombok.Value;

import javax.validation.constraints.Positive;

@Value
@DifferentFieldValues(firstFieldName = "sourceAccountId", secondFieldName = "destinationAccountId")
public final class TransferTO {
    private long sourceAccountId;
    private long destinationAccountId;
    @Positive
    private long cents;

    /*
    * constructor needed for hibernate
    * */
    public TransferTO(Long sourceAccountId, Long destinationAccountId, Long cents) {
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.cents = cents;
    }

    public TransferTO(Transfer transfer) {
        this.sourceAccountId = transfer.getSource().getAccountId();
        this.destinationAccountId = transfer.getDestination().getAccountId();
        this.cents = transfer.getCents();
    }
}

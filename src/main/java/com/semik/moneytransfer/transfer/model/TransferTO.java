package com.semik.moneytransfer.transfer.model;

import com.semik.moneytransfer.crosscutting.constraint.differentfieldvalues.DifferentFieldValues;
import lombok.Value;

import javax.validation.constraints.Positive;
import java.util.StringJoiner;

@Value
@DifferentFieldValues(firstFieldName = "sourceAccountId", secondFieldName = "destinationAccountId")
public final class TransferTO {
    private String sourceAccountId;
    private String destinationAccountId;
    @Positive
    private long cents;

    public TransferTO(Transfer transfer) {
        this.sourceAccountId = transfer.getSource().getAccountId();
        this.destinationAccountId = transfer.getDestination().getAccountId();
        this.cents = transfer.getCents();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TransferTO.class.getSimpleName() + "[", "]")
                .add("sourceAccountId='" + sourceAccountId + "'")
                .add("destinationAccountId='" + destinationAccountId + "'")
                .add("cents=" + cents)
                .toString();
    }
}

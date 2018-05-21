package com.semik.moneytransfer.core.exception;

public class TransferAmountMustBePositiveNumberException extends BusinessException {
    public TransferAmountMustBePositiveNumberException() {
        super("Transfer is allowed only for positive values");
    }
}

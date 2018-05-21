package com.semik.moneytransfer.core.exception;

public class TransferAmountMustBePositiveNumber extends BusinessException {
    public TransferAmountMustBePositiveNumber() {
        super("Transfer is allowed only for positive values");
    }
}

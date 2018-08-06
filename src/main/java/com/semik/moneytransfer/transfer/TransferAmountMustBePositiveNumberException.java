package com.semik.moneytransfer.transfer;

import com.semik.moneytransfer.crosscutting.exception.BusinessException;

public class TransferAmountMustBePositiveNumberException extends BusinessException {
    public TransferAmountMustBePositiveNumberException() {
        super("Transfer is allowed only for positive values");
    }
}

package com.semik.moneytransfer.transfer;

import com.semik.moneytransfer.crosscutting.exception.BusinessException;

import java.text.MessageFormat;

public class NoSufficientMeansException extends BusinessException {
    public NoSufficientMeansException(long balanceInCents, long centsToCharge) {
        super(MessageFormat.format("No sufficient means to charge balance with {0} cents for {1}", balanceInCents, centsToCharge));
    }
}

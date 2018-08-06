package com.semik.moneytransfer.account;

import com.semik.moneytransfer.crosscutting.exception.BusinessException;

public class NoSuchAccountException extends BusinessException {
    public NoSuchAccountException(long id) {
        super("There is no account with id " + id);
    }
}

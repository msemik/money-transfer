package com.semik.moneytransfer.core.exception;

public class NoSuchAccountException extends BusinessException{
    public NoSuchAccountException(long id) {
        super("There is no account with id " + id);
    }
}

package com.semik.moneytransfer.account.exception;

import com.semik.moneytransfer.crosscutting.exception.BusinessException;
import org.springframework.dao.OptimisticLockingFailureException;

public class ConcurrentAccountModificationException extends BusinessException {
    public ConcurrentAccountModificationException(OptimisticLockingFailureException ex) {
        super("Concurrent modification of account " + ex.getMessage().substring("Optimistic lock exception on saving entity: Document}".length()));
    }
}

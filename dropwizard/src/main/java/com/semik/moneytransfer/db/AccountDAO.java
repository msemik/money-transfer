package com.semik.moneytransfer.db;

import com.semik.moneytransfer.core.Account;
import com.semik.moneytransfer.core.exception.NoSuchAccountException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.SessionFactory;

import java.util.List;

public class AccountDAO extends AbstractDAO<Account> {
    private static int QUERY_TIMEOUT_SEC = 2;

    public AccountDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Account find(Long id) {
        return get(id);
    }

    public Account getAndLock(long id) {
        Account account = currentSession()
                .get(Account.class, id, new LockOptions(LockMode.PESSIMISTIC_WRITE)
                        .setTimeOut(QUERY_TIMEOUT_SEC * 1000));
        if(account == null){
            throw new NoSuchAccountException(id);
        }
        return account;
    }

    public List<Account> getAll() {
        return currentSession()
                .createQuery("FROM Account")
                .list();
    }
}

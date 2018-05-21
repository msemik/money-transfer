package com.semik.moneytransfer.db;

import com.semik.moneytransfer.core.Transfer;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class TransferDAO extends AbstractDAO<Transfer> {
    public TransferDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List getAll() {
        return list(namedQuery("com.semik.moneytransfer.getAll"));
    }

    public void save(Transfer transfer) {
        persist(transfer);
    }
}

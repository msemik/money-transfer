package com.semik.moneytransfer.resources;

import com.codahale.metrics.annotation.Timed;
import com.semik.moneytransfer.api.TransferTO;
import com.semik.moneytransfer.core.Account;
import com.semik.moneytransfer.core.Transfer;
import com.semik.moneytransfer.db.AccountDAO;
import com.semik.moneytransfer.db.TransferDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/money-transfer")
@Produces(MediaType.APPLICATION_JSON)
public class MoneyTransferResource {
    private TransferDAO transferDAO;
    private AccountDAO accountDAO;

    public MoneyTransferResource(TransferDAO transferDAO, AccountDAO accountDAO) {
        this.transferDAO = transferDAO;
        this.accountDAO = accountDAO;
    }

    @POST
    @Timed
    @UnitOfWork
    public void exchange(TransferTO transferTO) {
        Account sourceAccount = accountDAO.getAndLock(transferTO.getSourceAccountId());
        Account destinationAccount = accountDAO.getAndLock(transferTO.getDestinationAccountId());
        Transfer transfer = new Transfer();
        transfer.exchange(sourceAccount, destinationAccount, transferTO.getCents());
        transferDAO.save(transfer);
    }

    @GET
    @Timed
    @UnitOfWork
    public List<Transfer> getAll(){
        return transferDAO.getAll();
    }

}
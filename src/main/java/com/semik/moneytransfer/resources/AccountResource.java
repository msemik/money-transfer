package com.semik.moneytransfer.resources;

import com.codahale.metrics.annotation.Timed;
import com.semik.moneytransfer.core.Account;
import com.semik.moneytransfer.db.AccountDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
    private AccountDAO accountDAO;

    public AccountResource(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("{id}")
    public Account get(@PathParam("id") Long id){
        return accountDAO.find(id);
    }

}

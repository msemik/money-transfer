package com.semik.moneytransfer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.semik.moneytransfer.core.Account;
import com.semik.moneytransfer.core.Transfer;
import com.semik.moneytransfer.core.exception.BusinessExceptionMapper;
import com.semik.moneytransfer.db.AccountDAO;
import com.semik.moneytransfer.db.TransferDAO;
import com.semik.moneytransfer.resources.AccountResource;
import com.semik.moneytransfer.resources.MoneyTransferResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MoneyTransferApplication extends Application<MoneyTransferConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MoneyTransferApplication().run(args);
    }

    @Override
    public String getName() {
        return "MoneyTransfer";
    }

    @Override
    public void run(final MoneyTransferConfiguration configuration,
                    final Environment environment) {

        TransferDAO transferDAO = new TransferDAO(hibernate.getSessionFactory());
        AccountDAO accountDAO = new AccountDAO(hibernate.getSessionFactory());
        JerseyEnvironment jersey = environment.jersey();
        MoneyTransferResource moneyTransferResource = new MoneyTransferResource(transferDAO, accountDAO);
        jersey.register(moneyTransferResource);
        AccountResource accountResource = new AccountResource(accountDAO);
        jersey.register(accountResource);
        jersey.register(new BusinessExceptionMapper());

    }

    private final HibernateBundle<MoneyTransferConfiguration> hibernate = new HibernateBundle<MoneyTransferConfiguration>(Transfer.class, Account.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(MoneyTransferConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<MoneyTransferConfiguration> bootstrap) {
        bootstrap.getObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        bootstrap.getObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        bootstrap.addBundle(hibernate);
    }

}

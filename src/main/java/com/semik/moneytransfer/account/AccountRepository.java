package com.semik.moneytransfer.account;

import com.semik.moneytransfer.account.exception.NoSuchAccountException;
import com.semik.moneytransfer.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.Logger;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, String>{
    default Mono<Account> findByIdOrThrow(String accountId) {
        return findById(accountId)
                .switchIfEmpty(Mono.error(new NoSuchAccountException(accountId)));
    }
}

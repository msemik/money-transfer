package com.semik.moneytransfer.account;

import com.semik.moneytransfer.account.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountRepository accountRepository;

    @GetMapping("{id}")
    public Mono<Account> findById(@PathVariable("id") String id) {
        return accountRepository.findById(id);
    }

    @GetMapping
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }
}

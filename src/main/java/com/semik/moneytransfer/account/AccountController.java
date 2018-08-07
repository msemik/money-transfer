package com.semik.moneytransfer.account;

import com.semik.moneytransfer.account.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountRepository accountRepository;

    @GetMapping("{id}")
    public Optional<Account> findById(@PathVariable("id") Long id) {
        return accountRepository.findById(id);
    }

    @GetMapping
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}

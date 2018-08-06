package com.semik.moneytransfer.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;


    @GetMapping("{id}")
    public Optional<Account> findById(@PathVariable("id") Long id) {
        return accountRepository.findById(id);
    }

    @GetMapping
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}

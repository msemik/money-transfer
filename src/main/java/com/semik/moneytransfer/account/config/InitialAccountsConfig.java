package com.semik.moneytransfer.account.config;

import com.semik.moneytransfer.account.AccountRepository;
import com.semik.moneytransfer.account.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
public class InitialAccountsConfig {
    private final AccountRepository accountRepository;

    @Bean
    CommandLineRunner initMongoDB() {
        return (args) -> {
            accountRepository.deleteAll()
                    .subscribe(null, null,
                            () -> {
                                Stream.of(
                                        new Account("1", "Jolanta", "Pusta", 0L, null),
                                        new Account("2", "Grześ", "Zworkiem", 10000L, null),
                                        new Account("3", "Jaś", "Pełny", 9223372036854775807L, null))
                                        .collect(Collectors.toList())
                                        .forEach(account -> accountRepository.save(account)
                                                .subscribe(
                                                        acc -> System.out.println("Saved " + acc),
                                                        err -> {
                                                            err.printStackTrace();
                                                        }));
                            });
        };
    }

}

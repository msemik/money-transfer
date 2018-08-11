package com.semik.moneytransfer.transfer;

import com.semik.moneytransfer.account.AccountRepository;
import com.semik.moneytransfer.account.exception.ConcurrentAccountModificationException;
import com.semik.moneytransfer.account.model.Account;
import com.semik.moneytransfer.transfer.model.Transfer;
import com.semik.moneytransfer.transfer.model.TransferTO;
import com.semik.moneytransfer.transfer.service.TransferWebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("transfer")
@RequiredArgsConstructor
@Slf4j
public class TransferController {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransferWebSocketService transferWebSocketService;

    @PostMapping
    @Transactional
    public Mono<Void> exchange(@Valid @NotNull @RequestBody TransferTO transferTO) {
        return accountRepository.findByIdOrThrow(transferTO.getSourceAccountId())
                .zipWith(accountRepository.findByIdOrThrow(transferTO.getDestinationAccountId()))
                .flatMap(tuple -> {
                    return Transfer.exchange(tuple.getT1(), tuple.getT2(), transferTO.getCents())
                            .zipWhen(t -> accountRepository.saveAll(asList(tuple.getT1(), tuple.getT2())).collectList())
                            .map(t -> t.getT1());
                })
                .doOnNext(transferWebSocketService::onTransferExchanged)
                .onErrorMap(OptimisticLockingFailureException.class, ConcurrentAccountModificationException::new)
                .then()
                .checkpoint();
    }

    @GetMapping
    public Flux<TransferTO> findAll() {
        return transferRepository.findAllTO();
    }

}
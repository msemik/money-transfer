package com.semik.moneytransfer.transfer;

import com.semik.moneytransfer.account.model.Account;
import com.semik.moneytransfer.account.AccountRepository;
import com.semik.moneytransfer.transfer.event.TransferExchangedEvent;
import com.semik.moneytransfer.transfer.model.Transfer;
import com.semik.moneytransfer.transfer.model.TransferTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final ApplicationEventPublisher publisher;

    @PostMapping
    @Transactional
    public void exchange(@Valid @NotNull @RequestBody TransferTO transferTO) {
        Account sourceAccount;
        Account destinationAccount;

        if(transferTO.getSourceAccountId() < transferTO.getDestinationAccountId()){
            sourceAccount = accountRepository.getOneLocking(transferTO.getSourceAccountId());
            destinationAccount = accountRepository.getOneLocking(transferTO.getDestinationAccountId());
        }else {
            destinationAccount = accountRepository.getOneLocking(transferTO.getDestinationAccountId());
            sourceAccount = accountRepository.getOneLocking(transferTO.getSourceAccountId());
        }

        Transfer transfer = Transfer.exchange(sourceAccount, destinationAccount, transferTO.getCents());
        Transfer savedTransfer = transferRepository.save(transfer);
        publisher.publishEvent(new TransferExchangedEvent(savedTransfer));
    }

    @GetMapping
    public List<TransferTO> findAll() {
        return transferRepository.findAllTO();
    }

}
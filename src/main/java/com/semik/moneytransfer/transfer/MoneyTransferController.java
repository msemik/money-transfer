package com.semik.moneytransfer.transfer;

import com.semik.moneytransfer.account.Account;
import com.semik.moneytransfer.account.AccountRepository;
import com.semik.moneytransfer.transfer.event.TransferExchangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("money-transfer")
@RequiredArgsConstructor
public class MoneyTransferController {
    private final MoneyTransferRepository moneyTransferRepository;
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

        Transfer transfer = new Transfer();
        transfer.exchange(sourceAccount, destinationAccount, transferTO.getCents());
        Transfer savedTransfer = moneyTransferRepository.save(transfer);
        publisher.publishEvent(new TransferExchangedEvent(savedTransfer));
    }

    @GetMapping
    public List<TransferTO> findAll() {
        return moneyTransferRepository.findAllTO();
    }

}
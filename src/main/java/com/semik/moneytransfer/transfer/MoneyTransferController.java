package com.semik.moneytransfer.transfer;

import com.semik.moneytransfer.account.Account;
import com.semik.moneytransfer.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("money-transfer")
public class MoneyTransferController {
    @Autowired
    private MoneyTransferRepository moneyTransferRepository;
    @Autowired
    private AccountRepository accountReposiotry;

    @PostMapping
    @Transactional
    public void exchange(@Valid @NotNull @RequestBody TransferTO transferTO) {
        Account sourceAccount;
        Account destinationAccount;

        if(transferTO.getSourceAccountId() < transferTO.getDestinationAccountId()){
            sourceAccount = accountReposiotry.getOneLocking(transferTO.getSourceAccountId());
            destinationAccount = accountReposiotry.getOneLocking(transferTO.getDestinationAccountId());
        }else {
            destinationAccount = accountReposiotry.getOneLocking(transferTO.getDestinationAccountId());
            sourceAccount = accountReposiotry.getOneLocking(transferTO.getSourceAccountId());
        }

        Transfer transfer = new Transfer();
        transfer.exchange(sourceAccount, destinationAccount, transferTO.getCents());
        moneyTransferRepository.save(transfer);
    }

    @GetMapping
    public List<TransferTO> findAll() {
        return moneyTransferRepository.findAllTO();
    }

}
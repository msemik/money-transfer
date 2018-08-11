package com.semik.moneytransfer.transfer.service;


import com.semik.moneytransfer.account.model.AccountState;
import com.semik.moneytransfer.transfer.model.Transfer;
import com.semik.moneytransfer.transfer.model.TransferTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public final class TransferWebSocketService {
    @Autowired
    private SimpMessagingTemplate template;

    public void onTransferExchanged(Transfer transfer) {
        AccountState source = transfer.getSource();
        AccountState destination = transfer.getDestination();
        TransferTO payload = new TransferTO(transfer);
        template.convertAndSend("/topic/transfer/" + source.getAccountId() + "/" + destination.getAccountId(), payload);
        if (log.isDebugEnabled()) {
            log.debug("Message onTransferExchanged sent: " + payload);
        }
    }
}

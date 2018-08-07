package com.semik.moneytransfer.transfer.service;


import com.semik.moneytransfer.account.model.AccountState;
import com.semik.moneytransfer.transfer.event.TransferExchangedEvent;
import com.semik.moneytransfer.transfer.model.Transfer;
import com.semik.moneytransfer.transfer.model.TransferTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransferWebSocketService {
    @Autowired
    private SimpMessagingTemplate template;

    @EventListener
    public void on(TransferExchangedEvent event) {
        Transfer transfer = event.getTransfer();
        AccountState source = transfer.getSource();
        AccountState destination = transfer.getDestination();
        template.convertAndSend("/topic/transfer/" + source.getAccountId() + "/" + destination.getAccountId(), new TransferTO(transfer));
    }
}

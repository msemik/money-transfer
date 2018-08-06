package com.semik.moneytransfer.transfer.event;

import com.semik.moneytransfer.transfer.Transfer;
import org.springframework.context.ApplicationEvent;

public final class TransferExchangedEvent extends ApplicationEvent {
    public TransferExchangedEvent(Transfer exchangedTransfer) {
        super(exchangedTransfer);
    }

    public Transfer getTransfer(){
        return ((Transfer) this.getSource());
    }
}

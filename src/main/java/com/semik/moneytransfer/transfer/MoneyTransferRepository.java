package com.semik.moneytransfer.transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoneyTransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT new com.semik.moneytransfer.transfer.TransferTO(source.accountId, destination.accountId, cents) FROM Transfer")
    List<TransferTO> findAllTO();
}

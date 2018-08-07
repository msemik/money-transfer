package com.semik.moneytransfer.transfer;

import com.semik.moneytransfer.transfer.model.Transfer;
import com.semik.moneytransfer.transfer.model.TransferTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT new com.semik.moneytransfer.transfer.model.TransferTO(source.accountId, destination.accountId, cents) FROM Transfer")
    List<TransferTO> findAllTO();
}
